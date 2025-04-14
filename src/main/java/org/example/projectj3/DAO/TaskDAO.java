package org.example.projectj3.DAO;

import org.example.projectj3.pojo.Task;
import java.util.List;

public interface TaskDAO {
    List<Task> getAllTasks();

    List<Task> getTasksByUserId(int userId);

    Task getTaskById(int taskId);

    boolean createTask(Task task, int userId);

    boolean updateTask(Task task);

    boolean deleteTask(int taskId);

    boolean toggleTaskCompletion(int taskId);
}
