package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import util.ManagerSaveException;


import java.util.*;


public class InMemoryTaskManager implements TaskManager {

    private final InMemoryHistoryManager inMemoryHistoryManager;

    private final HashMap<Integer, Task> tasksList = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasksList = new HashMap<>();
    private final HashMap<Integer, Epic> epicsList = new HashMap<>();


    public InMemoryTaskManager() {
        inMemoryHistoryManager = new InMemoryHistoryManager();
    }

    public void addTask(Task task) throws ManagerSaveException {
        tasksList.put(task.getId(), task);
    }

    public void addEpic(Epic task) throws ManagerSaveException {
        epicsList.put(task.getId(), task);
    }

    public void addSubtask(Subtask subtask) throws ManagerSaveException {
        subtasksList.put(subtask.getId(), subtask);
    }

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    public TreeSet<Task> getPrioritizedTasks() throws ManagerSaveException {
        TreeSet<Task> s = new TreeSet<>(Comparator.comparing(Task::getStartTime));

        s.addAll(tasksList.values());
        s.addAll(epicsList.values());
        s.addAll(subtasksList.values());

        return s;
    }

    public Task getTaskById(int id) throws ManagerSaveException {
        if (tasksList.get(id) != null) {
            inMemoryHistoryManager.add(tasksList.get(id));
            return tasksList.get(id);
        } else {
            return null;
        }
    }

    public Epic getEpicById(int id) throws ManagerSaveException {
        if (epicsList.get(id) != null) {
            inMemoryHistoryManager.add(epicsList.get(id));
            return epicsList.get(id);
        } else {
            return null;
        }
    }

    public Subtask getSubtaskById(int id) throws ManagerSaveException {
        if (subtasksList.get(id) !=  null) {
            inMemoryHistoryManager.add(subtasksList.get(id));
            return subtasksList.get(id);
        } else {
            return null;
        }
    }

    public void removeTaskByName(Task task) {
        int TaskId = task.getId();
        tasksList.remove(TaskId);
    }

    public void removeSubtaskByName(Subtask task) {
        int TaskId = task.getId();
        subtasksList.remove(TaskId);
    }

    public void removeEpic(Epic task) {
        epicsList.remove(task.getId());
    }

    public void removeAllEpics() {
        epicsList.clear();
    }

    public void removeAllSubtasks(){subtasksList.clear();}

    public void removeAllTasks() {
        tasksList.clear();
    }

    public List<Task> getTasksList() {
        return new ArrayList<>(tasksList.values());
    }

    public List<Subtask> getSubtasksList() {
        return new ArrayList<>(subtasksList.values());
    }

    public List<Epic> getEpicsList() {
        return new ArrayList<>(epicsList.values());
    }

}