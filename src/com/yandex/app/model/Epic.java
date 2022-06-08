package com.yandex.app.model;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String name, String description) {
        super(name, description);
        subtasks = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
        updateCurrentStatus();
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
        updateCurrentStatus();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    @Override
    public void setStatus(Status status) {
    }

    private int countSubtasksStatuses(Status status) {
        int countNew = 0;
        for (Subtask substask : subtasks) {
            if (substask.getStatus() == status) {
                countNew++;
            }
        }
        return countNew;
    }

    public void updateCurrentStatus() {
        if (subtasks.isEmpty() || countSubtasksStatuses(Status.NEW) == subtasks.size()) {
            this.status = Status.NEW;
        } else if (countSubtasksStatuses(Status.DONE) == subtasks.size()) {
            this.status = Status.DONE;
        } else {
            this.status = Status.IN_PROGRESS;
        }
    }
}
