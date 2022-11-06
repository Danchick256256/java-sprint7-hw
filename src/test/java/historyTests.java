import controller.FileBackedTasksManager;
import model.Task;
import org.junit.Test;
import util.ManagerSaveException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class historyTests {
    @Test
    public void reverseHistory() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getTaskById(1);

        final List<Task> history = fileBackedTasksManager.getHistory();
        System.out.println(history);
        assertEquals(history.get(0), fileBackedTasksManager.getTaskById(1));
    }

    @Test
    public void duplicateHistory() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(1);

        final List<Task> history = fileBackedTasksManager.getHistory();
        assertNotNull(history.toString(), "История не пустая.");
        assertEquals(1, history.size());
    }

    @Test
    public void history() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        fileBackedTasksManager.getTaskById(1);
        final List<Task> history = fileBackedTasksManager.getHistory();
        assertNotNull(history.toString(), "История не пустая.");
        assertEquals(1, history.size());
    }

    @Test
    public void getHistory() throws ManagerSaveException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        fileBackedTasksManager.getTaskById(1);
        final List<Task> history = fileBackedTasksManager.getHistory();
        assertEquals(fileBackedTasksManager.getHistory(), history);
    }
}
