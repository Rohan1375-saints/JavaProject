package org.example.projectj3.tables;

import org.example.projectj3.DAO.TaskDAO;
import org.example.projectj3.pojo.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskTable implements TaskDAO {
    private final Connection connection;

    public TaskTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Getter for all tasks
     * @return tasks
     */
    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM task_table WHERE deleted = 0";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(
                        resultSet.getInt("Task_ID"),
                        resultSet.getString("Task_Title"),
                        resultSet.getString("Task_Description"),
                        resultSet.getBoolean("is_Completed"),
                        resultSet.getBoolean("is_Pinned")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Getter for tasks by user ID
     * @param userId
     * @return tasks
     */
    @Override
    public List<Task> getTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT t.* FROM task_table t " +
                "JOIN user_task_table ut ON t.Task_ID = ut.Task_ID " +
                "WHERE ut.User_ID = ? AND t.deleted = 0";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(new Task(
                        resultSet.getInt("Task_ID"),
                        resultSet.getString("Task_Title"),
                        resultSet.getString("Task_Description"),
                        resultSet.getBoolean("is_Completed"),
                        resultSet.getBoolean("is_Pinned")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Getter for task by ID
     * @param taskId
     * @return task
     */
    @Override
    public Task getTaskById(int taskId) {
        String query = "SELECT * FROM task_table WHERE Task_ID = ? AND deleted = 0";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, taskId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Task(
                        resultSet.getInt("Task_ID"),
                        resultSet.getString("Task_Title"),
                        resultSet.getString("Task_Description"),
                        resultSet.getBoolean("is_Completed"),
                        resultSet.getBoolean("is_Pinned")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create task
     * @param task
     * @param userId
     * @return boolean
     */
    @Override
    public boolean createTask(Task task, int userId) {
        String query = "INSERT INTO task_table (Task_Title, Task_Description, Task_Due_Date, is_Completed, is_Pinned) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getDueDate());
            statement.setBoolean(4, task.isCompleted());
            statement.setBoolean(5, task.isPinned());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    int taskId = keys.getInt(1);
                    task.setTaskId(taskId);

                    String linkQuery = "INSERT INTO user_task_table (User_ID, Task_ID) VALUES (?, ?)";
                    try (PreparedStatement linkStatement = connection.prepareStatement(linkQuery)) {
                        linkStatement.setInt(1, userId);
                        linkStatement.setInt(2, taskId);
                        linkStatement.executeUpdate();
                    }
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Update task
     * @param task
     * @return boolean
     */
    @Override
    public boolean updateTask(Task task) {
        String query = "UPDATE task_table SET Task_Title = ?, Task_Description = ?, is_Completed = ?, is_Pinned = ? WHERE Task_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setBoolean(4, task.isPinned());
            statement.setInt(5, task.getTaskId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Delete task
     * @param taskId
     * @return boolean
     */
    @Override
    public boolean deleteTask(int taskId) {
        String query = "UPDATE task_table SET deleted = 1 WHERE Task_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, taskId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Toggle task completion
     * @param taskId
     * @return boolean
     */
    @Override
    public boolean toggleTaskCompletion(int taskId) {
        String query = "UPDATE task_table SET is_Completed = NOT is_Completed WHERE Task_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, taskId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Assign task to user
     * @param userId
     * @param taskId
     * @return boolean
     */
    public boolean assignTaskToUser(int userId, int taskId) {
        String query = "INSERT INTO user_task_table (User_ID, Task_ID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setInt(2, taskId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
