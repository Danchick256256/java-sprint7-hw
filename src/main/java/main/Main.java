package main;

import controller.FileBackedTasksManager;
import util.ManagerSaveException;

// где смог убрал static, тесты у меня все работают

public class Main {

    public static void main(String[] args) throws ManagerSaveException {

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager("tasks.csv");
        FileBackedTasksManager newFileBackedTasksManager = new FileBackedTasksManager("tasks.csv");


        /* Task firstTask = new Task("FirstTask", "FirstTask description", LocalDateTime.now(), Duration.ZERO);
        firstTask.setStatus(Status.IN_PROGRESS);
        fileBackedTasksManager.addTask(firstTask);

        Task secondTask = new Task("SecondTask", "SecondTask description", LocalDateTime.now(), Duration.ZERO);
        secondTask.setStatus(Status.DONE);
        fileBackedTasksManager.addTask(secondTask);


        Epic firstEpic = new Epic("FirstEpic", "FirstEpic description");

        Subtask firstSubtask = new Subtask("FirstSubtask", "FirstSubtask description", firstEpic.getId(), LocalDateTime.now(), Duration.ZERO);
        Subtask secondSubtask = new Subtask("SecondSubtask", "SecondSubtask description", firstEpic.getId(), LocalDateTime.now(), Duration.ZERO);
        Subtask thirdSubtask = new Subtask("ThirdSubtask", "ThirdSubtask description", firstEpic.getId(), LocalDateTime.now(), Duration.ZERO);


        firstEpic.addSubTask(firstSubtask);
        firstEpic.addSubTask(secondSubtask);
        firstEpic.addSubTask(thirdSubtask);

        fileBackedTasksManager.addSubtask(firstSubtask);
        fileBackedTasksManager.addSubtask(secondSubtask);
        fileBackedTasksManager.addSubtask(thirdSubtask);
        fileBackedTasksManager.addEpic(firstEpic); */

        //System.out.println(fileBackedTasksManager.getPrioritizedTasks());

        System.out.println(fileBackedTasksManager.getTaskById(1));
        System.out.println(fileBackedTasksManager.getSubtaskById(4));

        System.out.println("\n\nTesting history list \n" + fileBackedTasksManager.getHistory());

        System.out.println("\n\nHistory list \n" + newFileBackedTasksManager.getHistory());

    }

}