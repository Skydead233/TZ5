package com.yandex.app;
import com.yandex.app.service.Managers;
import com.yandex.app.service.TaskManager;
import com.yandex.app.service.Tests;

public class Main {

    public static void main(String[] args) {
	    TaskManager manager = Managers.getDefault();

        Tests.testCreateNewTask(manager);
        Tests.clearAllDataInTests(manager);

	    Tests.testDeleteAllTasks(manager);
        Tests.clearAllDataInTests(manager);

        Tests.testGetHistoryWithoutCalls();
        Tests.clearAllDataInTests(manager);

        Tests.testGetHistoryWithTaskCalls();
        Tests.clearAllDataInTests(manager);

        Tests.testGetHistoryWithMixedCalls();
        Tests.clearAllDataInTests(manager);
    }
}
