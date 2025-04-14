package org.example.projectj3.tests;

import org.example.projectj3.Database.Database;
import org.example.projectj3.pojo.Task;
import org.example.projectj3.tables.TaskTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TaskTableTest {

    public static void main(String[] args) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Failed to establish a database connection.");
            return;
        }

        TaskTable taskTable = new TaskTable(connection);

        testCreateTask(taskTable);
        testAssignTaskToUser(taskTable);
        testRetrieveTasksByUserId(taskTable);
        testGetAllTasks(taskTable);
        testGetTaskById(taskTable);
        testUpdateTask(taskTable);
        //testDeleteTask(taskTable);

        Database.getInstance().closeConnection();
    }

    private static void testAssignTaskToUser(TaskTable taskTable) {
        System.out.println("\nRunning testAssignTaskToUser...");
        int validUserId = 1; //  is a valid User_ID from database
        int validTaskId = 2; //  is a valid Task_ID from database
        boolean result = taskTable.assignTaskToUser(validUserId, validTaskId);
        System.out.println("Task assigned to user: " + result);
    }

    private static void testRetrieveTasksByUserId(TaskTable taskTable) {
        System.out.println("\nRunning testRetrieveTasksByUserId...");
        int validUserId = 1; // is a valid User_ID from database
        List<Task> tasks = taskTable.getTasksByUserId(validUserId);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private static void testCreateTask(TaskTable taskTable) {
        System.out.println("\nRunning testCreateTask...");
        Task task = new Task("Sample Task", "This is a sample task description.", false, false);
        boolean result = taskTable.createTask(task, 1);
        System.out.println("Task created: " + result);
        System.out.println("Created Task ID: " + task.getTaskId());
    }

    private static void testGetAllTasks(TaskTable taskTable) {
        System.out.println("\nRunning testGetAllTasks...");
        List<Task> tasks = taskTable.getAllTasks();
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private static void testGetTaskById(TaskTable taskTable) {
        System.out.println("\nRunning testGetTaskById...");
        Task task = taskTable.getTaskById(2); // an existing, non-deleted task ID
        if (task != null) {
            System.out.println("Task found: " + task);
        } else {
            System.out.println("No task found with the given ID.");
        }
    }

    private static void testUpdateTask(TaskTable taskTable) {
        System.out.println("\nRunning testUpdateTask...");
        Task task = new Task(1, "Updated Task", "Updated task description.", true, true);
        boolean result = taskTable.updateTask(task);
        System.out.println("Task updated: " + result);
    }

    private static void testDeleteTask(TaskTable taskTable) {
        System.out.println("\nRunning testDeleteTask...");
        boolean result = taskTable.deleteTask(1); // a valid taskId in database
        System.out.println("Task deleted: " + result);
    }
}
