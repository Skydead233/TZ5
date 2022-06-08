package com.yandex.app.service;

import com.yandex.app.model.Epic;
import com.yandex.app.model.Subtask;
import com.yandex.app.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Long, Task> tasks = new HashMap<>();
    private HashMap<Long, Epic> epics = new HashMap<>();
    private HashMap<Long, Subtask> subtasks = new HashMap<>();
    private HistoryManager history = Managers.getDefaultHistory();

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTask(long id) {
        if (tasks.get(id) != null) {
            history.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public void createNewTask(Task task) {
        long nextId = IdGenerate.getNextId();
        task.setId(nextId);
        tasks.put(nextId, task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void deleteTask(long id) {
        tasks.remove(id);
        history.remove(id);
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpic(long id) {
        if (epics.get(id) != null) {
            history.add(epics.get(id));
        }
        return epics.get(id);
    }


    @Override
    public void createNewEpic(Epic epic) {
        long nextId = IdGenerate.getNextId();
        epic.setId(nextId);
        epics.put(nextId, epic);
    }

    @Override
    public void deleteEpic(long id) {
        List<Subtask> epicSubtasks = epics.get(id).getSubtasks();
        for (Subtask subtask : epicSubtasks) {
            subtasks.remove(subtask.getId());
        }
        epicSubtasks.clear();
        epics.remove(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    @Override
    public List<Subtask> getAllSubtaskEpic(long id) {
        if (epics.containsKey(id)) {
            return new ArrayList<>(epics.get(id).getSubtasks());
        }
        return null;
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void createNewSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.getEpicId())) {
            long nextId = IdGenerate.getNextId();
            subtask.setId(nextId);
            subtasks.put(nextId, subtask);
            epics.get(subtask.getEpicId()).addSubtask(subtask);
        }
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            epic.updateCurrentStatus();
        }
        subtasks.clear();
    }

    @Override
    public Subtask getSubtask(long id) {
        if (subtasks.get(id) != null) {
            history.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public void deleteSubtask(long id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getEpicId());
            subtasks.remove(id);
            epic.removeSubtask(subtask);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Epic epic = epics.get(subtask.getEpicId());
            subtasks.put(subtask.getId(), subtask);
            epic.removeSubtask(subtask);
            epic.addSubtask(subtask);
        }
    }
}


