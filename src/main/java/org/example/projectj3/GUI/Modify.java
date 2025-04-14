package org.example.projectj3.GUI;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.projectj3.Database.Database;
import org.example.projectj3.tables.TagTable;
import org.example.projectj3.tables.TaskTable;
import org.example.projectj3.pojo.Task;

import java.sql.Connection;
import java.util.List;

public class Modify extends Application {
    private final int taskId;

    public Modify(int taskId) {
        this.taskId = taskId;
    }

    /**
     * Start method for Modify
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        Task task = fetchTaskDetails(taskId);
        if (task == null) {
            System.out.println("Task not found!");
            return;
        }
        Text updateTaskLabel = new Text("Update Task");
        Text updateDescriptionLabel = new Text("Update Description");
        Text dateLabel = new Text("Date");
        Text tagLabel = new Text("Tag");

        TextArea taskTitleField = new TextArea(task.getTitle());
        TextArea taskDescriptionField = new TextArea(task.getDescription());
        DatePicker dueDatePicker = new DatePicker();
        ComboBox<String> tagComboBox = new ComboBox<>();
        Button submitButton = new Button("Submit");

        dueDatePicker.setPromptText(task.getDueDate() != null ? task.getDueDate() : "Set due date");
        loadTags(tagComboBox); // Load tags into ComboBox

        updateTaskLabel.setStyle("-fx-font-size: 21px; -fx-font-weight: bold;");
        updateDescriptionLabel.setStyle("-fx-font-size: 21px; -fx-font-weight: bold;");
        dateLabel.setStyle("-fx-font-size: 21px; -fx-font-weight: bold;");
        tagLabel.setStyle("-fx-font-size: 21px; -fx-font-weight: bold;");
        submitButton.setStyle("-fx-background-color: #8cfa8c; -fx-font-size: 15; -fx-font-weight: bold;");

        taskTitleField.setMaxSize(500, 50);
        taskDescriptionField.setMaxSize(500, 150);

        VBox form = new VBox(10, updateTaskLabel, taskTitleField, updateDescriptionLabel, taskDescriptionField, dateLabel, dueDatePicker, tagLabel, tagComboBox, submitButton);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(20));

        BorderPane layout = new BorderPane();
        layout.setCenter(form);
        layout.setStyle("-fx-background-color: TAN;");

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Modify Task");
        stage.show();

        submitButton.setOnAction(e -> {
            String updatedTitle = taskTitleField.getText();
            String updatedDescription = taskDescriptionField.getText();
            String updatedDueDate = dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : null;
            String selectedTag = tagComboBox.getValue();

            if (updatedTitle.isEmpty() || updatedDescription.isEmpty() || updatedDueDate == null || selectedTag == null) {
                System.out.println("Please fill in all fields!");
                return;
            }

            task.setTitle(updatedTitle);
            task.setDescription(updatedDescription);
            task.setDueDate(updatedDueDate);

            boolean success = updateTask(task);
            if (success) {
                System.out.println("Task updated successfully!");
                stage.close();
            } else {
                System.out.println("Failed to update task.");
            }
        });
    }

    /**
     * Fetch task details from the database
     * @param taskId
     * @return
     */
    private Task fetchTaskDetails(int taskId) {
        try (Connection connection = Database.getInstance().getConnection()) {
            TaskTable taskTable = new TaskTable(connection);
            return taskTable.getTaskById(taskId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Load tags into ComboBox
     * @param comboBox
     */
    private void loadTags(ComboBox<String> comboBox) {
        try (Connection connection = Database.getInstance().getConnection()) {
            TagTable tagTable = new TagTable(connection);
            List<String> tags = tagTable.getAllTagTitles();
            comboBox.getItems().addAll(tags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update task in the database
     * @param task
     * @return
     */
    private boolean updateTask(Task task) {
        try (Connection connection = Database.getInstance().getConnection()) {
            TaskTable taskTable = new TaskTable(connection);
            return taskTable.updateTask(task);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
