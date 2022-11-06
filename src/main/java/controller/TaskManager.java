package controller;

import model.Epic;
import model.Subtask;
import model.Task;
import util.ManagerSaveException;

import java.util.List;
import java.util.TreeSet;


public interface TaskManager {

    List<Task> getHistory();

    void addTask(Task task) throws ManagerSaveException;

    void addEpic(Epic task) throws ManagerSaveException;

    void addSubtask(Subtask task) throws ManagerSaveException;

    Task getTaskById(int id) throws ManagerSaveException;

    public TreeSet<Task> getPrioritizedTasks() throws ManagerSaveException;

    void removeTaskByName(Task task);

    void removeAllEpics();

    void removeAllTasks();


    List<Task> getTasksList();

    List<Subtask> getSubtasksList();

    List<Epic> getEpicsList();


}