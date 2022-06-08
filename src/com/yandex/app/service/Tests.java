package com.yandex.app.service;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;


import java.util.List;

public class Tests {
    public static void testCreateNewTask(TaskManager taskManager) {
        assertIntEquals(0, taskManager.getTasks().size(), "testCreateNewTask");

        taskManager.createNewTask(new Task("Task Name 1", "Task Description 1"));
        taskManager.createNewTask(new Task("Task Name 2", "Task Description 2"));

        assertIntEquals(2, taskManager.getTasks().size(), "testCreateNewTask");
    }

    public static void testDeleteAllTasks(TaskManager taskManager) {
        taskManager.createNewTask(new Task("Task Name 1", "Task Description 1"));
        taskManager.createNewTask(new Task("Task Name 2", "Task Description 2"));
        taskManager.createNewTask(new Task("Task Name 3", "Task Description 3"));

        taskManager.deleteAllTasks();

        assertIntEquals(0, taskManager.getTasks().size(), "testDeleteAllTasks");
    }

    public static void testGetHistoryWithoutCalls() {
        TaskManager taskManager = new InMemoryTaskManager();
        for (int i = 0; i < 10; i++) {
            taskManager.createNewTask(new Task("Task Name " + i, "Task Description " + i));
        }

        List<Task> history = taskManager.getHistory();

        assertIntEquals(0, history.size(), "testGetHistoryWithoutCalls");
    }

    public static void testGetHistoryWithTaskCalls() {
        TaskManager taskManager = new InMemoryTaskManager();
        for (int i = 0; i < 10; i++) {
            taskManager.createNewTask(new Task("Task Name " + i, "Task Description " + i));
            List<Task> tasks = taskManager.getTasks();
            taskManager.getTask(tasks.get(i).getId());
        }

        List<Task> history = taskManager.getHistory();

        assertIntEquals(10, history.size(), "testGetHistoryWithTaskCalls");

        List<Task> tasks = taskManager.getTasks();
        taskManager.getTask(tasks.get(0).getId());
        assertIntEquals(10, history.size(), "testGetHistoryWithTaskCalls");
    }

    public static void testGetHistoryWithMixedCalls() {
        TaskManager taskManager = new InMemoryTaskManager();
        taskManager.createNewTask(new Task("Task Name 1", "Task Description 1"));
        taskManager.createNewEpic(new Epic("Task Name 2", "Task Description 2"));
        taskManager.createNewSubtask(new Subtask("Task Name 3", "Task Description 3", taskManager.getEpics().get(0).getId()));

        taskManager.getTask(taskManager.getTasks().get(0).getId());
        taskManager.getEpic(taskManager.getEpics().get(0).getId());
        taskManager.getSubtask(taskManager.getSubtasks().get(0).getId());

        List<Task> history = taskManager.getHistory();

        assertIntEquals(3, history.size(), "testGetHistoryWithMixedCalls");
    }

    public static void clearAllDataInTests(TaskManager taskManager) {
        taskManager.deleteAllTasks();
        taskManager.deleteAllEpics();
        taskManager.deleteAllSubtasks();
    }

    public static void assertIntEquals(int expected, int result, String testName) {
        if (expected != result) {
            System.out.println(testName + " is failed");
            System.out.println("Expected: " + expected);
            System.out.println("Result: " + result);
        }
    }

    public static void assertStringEquals(String expected, String result, String testName) {
        if (expected.equals(result)) {
            System.out.println(testName + " is failed");
        }
    }
}
