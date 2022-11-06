import controller.FileBackedTasksManager;
import model.Task;
import org.junit.Test;
import util.ManagerSaveException;
import util.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class taskTests {
    @Test
    public void addNewTask() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        Task task = new Task("Test addNewTask", "Test addNewTask description", LocalDateTime.now(), Duration.ZERO);

        fileBackedTasksManager.addTask(task);

        final int taskId = task.getId();

        final Task savedTask = fileBackedTasksManager.getTaskById(taskId);

        assertEquals(savedTask.getStatus(), Status.NEW);

        final List<Task> tasks = fileBackedTasksManager.getTasksList();

        assertNotNull(tasks.toString(), "Задачи на возвращаются.");
    }

    @Test
    public void wrongIdentifier() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");

        final Task savedTask = fileBackedTasksManager.getTaskById(7);

        assertEquals(savedTask, null);
    }
}
