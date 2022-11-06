import controller.FileBackedTasksManager;
import model.Epic;
import model.Subtask;
import org.junit.Test;
import util.ManagerSaveException;
import util.Status;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class epicTests {

    @Test
    public void emptyEpic() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        Epic epic = new Epic("firstEpic", "desc");

        fileBackedTasksManager.addEpic(epic);

        final int taskId = epic.getId();

        final Epic savedTask = fileBackedTasksManager.getEpicById(taskId);

        assertEquals(epic, savedTask);

    }

    @Test
    public void addFirstEpic() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        Epic epic = new Epic("firstEpic", "desc");

        fileBackedTasksManager.addEpic(epic);

        final int taskId = epic.getId();

        final Epic savedTask = fileBackedTasksManager.getEpicById(taskId);

        assertEquals(savedTask.getStatus(), Status.NEW);

        Subtask subtask1 = new Subtask("FirstSubtask", "FirstSubtask description", savedTask.getId(), LocalDateTime.now(), Duration.ZERO);
        Subtask subtask2 = new Subtask("SecondSubtask", "SecondSubtask description", savedTask.getId(), LocalDateTime.now(), Duration.ZERO);

        savedTask.addSubTask(subtask1);
        savedTask.addSubTask(subtask2);

        fileBackedTasksManager.addSubtask(subtask1);
        fileBackedTasksManager.addSubtask(subtask2);

        assertEquals(savedTask.getStatus(), Status.NEW);

    }

    @Test
    public void addSecondEpic() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        Epic epic = new Epic("firstEpic", "desc");

        Subtask subtask1 = new Subtask("FirstSubtask", "FirstSubtask description", epic.getId(), LocalDateTime.now(), Duration.ZERO);
        Subtask subtask2 = new Subtask("SecondSubtask", "SecondSubtask description", epic.getId(), LocalDateTime.now(), Duration.ZERO);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);

        epic.addSubTask(subtask1);
        epic.addSubTask(subtask2);

        fileBackedTasksManager.addEpic(epic);

        fileBackedTasksManager.addSubtask(subtask1);
        fileBackedTasksManager.addSubtask(subtask2);

        assertEquals(epic.getStatus(), Status.DONE);

    }

    @Test
    public void addThirdEpic() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        Epic epic = new Epic("firstEpic", "desc");

        Subtask subtask1 = new Subtask("FirstSubtask", "FirstSubtask description", epic.getId(), LocalDateTime.now(), Duration.ZERO);
        Subtask subtask2 = new Subtask("SecondSubtask", "SecondSubtask description", epic.getId(), LocalDateTime.now(), Duration.ZERO);

        subtask1.setStatus(Status.NEW);
        subtask2.setStatus(Status.DONE);

        epic.addSubTask(subtask1);
        epic.addSubTask(subtask2);

        fileBackedTasksManager.addEpic(epic);

        fileBackedTasksManager.addSubtask(subtask1);
        fileBackedTasksManager.addSubtask(subtask2);

        assertEquals(epic.getStatus(), Status.IN_PROGRESS);
    }
}
