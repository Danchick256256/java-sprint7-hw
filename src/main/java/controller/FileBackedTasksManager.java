package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import util.ManagerSaveException;
import util.Status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;


public class FileBackedTasksManager extends InMemoryTaskManager{
    private final Path saveFile;
    InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    public FileBackedTasksManager(String saveFile){
        super();
        this.saveFile = Paths.get(saveFile);
    }

    protected void save() throws ManagerSaveException {
        loadTasksFromFile(saveFile);

        List<String> lines = new ArrayList<>();

        lines.add("ID,TYPE,NAME,STATUS,DESCRIPTION,EPIC,STARTTIME,DURATION");

        try {
            for (Epic task : super.getEpicsList()) {
                lines.add(task.toString());
            }
            for (Subtask task : super.getSubtasksList()) {
                lines.add(task.toString());
            }
            for (Task task : super.getTasksList()) {
                lines.add(task.toString());
            }
        } catch (NullPointerException ignored) {}

        lines.add("\n" + getHistory().toString());

        try {
            Files.write(saveFile,
                    lines,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        }
        catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при записи в файл: " + e.getMessage());
        }
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() throws ManagerSaveException {
        loadTasksFromFile(saveFile);
        return super.getPrioritizedTasks();
    }

    @Override
    public Task getTaskById(int num) throws ManagerSaveException {
        loadTasksFromFile(saveFile);
        Task task = super.getTaskById(num);
        save();
        return task;
    }

    @Override
    public Subtask getSubtaskById(int num) throws ManagerSaveException {
        loadTasksFromFile(saveFile);
        Subtask task = super.getSubtaskById(num);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int num) throws ManagerSaveException {
        loadTasksFromFile(saveFile);
        Epic task = super.getEpicById(num);
        save();
        return task;
    }

    @Override
    public void addTask(Task newTask) throws ManagerSaveException {
        loadTasksFromFile(saveFile);
        super.addTask(newTask);
        save();
    }

    @Override
    public void addEpic(Epic newTask) throws ManagerSaveException {
        loadTasksFromFile(saveFile);
        super.addEpic(newTask);
        save();
    }

    @Override
    public void addSubtask(Subtask newTask) throws ManagerSaveException {
        loadTasksFromFile(saveFile);
        super.addSubtask(newTask);
        save();
    }

    public void removeTaskById(Integer num) throws ManagerSaveException {
        inMemoryHistoryManager.remove(num);
        loadTasksFromFile(saveFile);
        super.removeTaskByName(super.getTaskById(num));
        save();
    }

    public void loadTasksFromFile(Path file) throws ManagerSaveException {

        List<String> lines;

        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при чтении файла " + file + ": " + e.getMessage());
        }

        //ID;TYPE;NAME;STATUS;DESCRIPTION;EPIC


        for (int i = 1; i < lines.size(); i++) {
            try {
                String[] type = lines.get(i).split(",");
                if (lines.get(i).isEmpty()) {
                    break;
                }
                if (lines.get(i) != null && Objects.equals(type[1], "EPIC")) {
                    String[] split = lines.get(i).split(",");
                    Epic epic;
                    if (split[6].equals("null") && split[7].equals("null")) {
                        epic = new Epic(Integer.parseInt(split[0]), split[2], Status.valueOf(split[3]), split[4]);
                    } else {
                        epic = new Epic(Integer.parseInt(split[0]), split[2], Status.valueOf(split[3]), split[4], LocalDateTime.parse(split[6]), Duration.parse(split[7]));
                    }
                    super.addTask(epic);
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }

        }

        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).isEmpty()) {
                break;

            } else if (lines.get(i) != null && !lines.get(i).contains(",EPIC,")) {
                String[] split = lines.get(i).split(",");
                if (Objects.equals(split[1], "TASK")) {
                    Task task = new Task(Integer.parseInt(split[0]), split[2], Status.valueOf(split[3]), split[4], LocalDateTime.parse(split[6]), Duration.parse(split[7]));
                    super.addTask(task);
                } else if (split[1].equals("SUBTASK")) {
                    Subtask subtask = new Subtask(Integer.parseInt(split[0]), split[2], Status.valueOf(split[3]), split[4], Integer.parseInt(split[5]),LocalDateTime.parse(split[6]), Duration.parse(split[7]));
                    super.addSubtask(subtask);
                }
            }
        }
    }
}