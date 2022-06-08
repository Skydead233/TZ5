package com.yandex.app.service;
import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;
import java.util.List;

public interface TaskManager {
    List<Task> getTasks();
    void deleteAllTasks();
    Task getTask(long id);
    void createNewTask(Task task);
    void updateTask(Task newTask);
    void deleteTask(long id);

    List<Epic> getEpics();
    void deleteAllEpics();
    Epic getEpic(long id);
    void createNewEpic(Epic epic);
    void updateEpic(Epic newEpic);
    void deleteEpic(long id);
    List<Subtask> getAllSubtaskEpic(long id);

    List<Subtask> getSubtasks();
    void deleteAllSubtasks();
    Subtask getSubtask(long id);
    void createNewSubtask(Subtask subtask);
    void updateSubtask(Subtask newSubtask);
    void deleteSubtask(long id);

    List<Task> getHistory();
}
