package org.example.projectj3.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projectj3.Database.Database;
import org.example.projectj3.pojo.Task;
import org.example.projectj3.tables.TagTable;
import org.example.projectj3.tables.TaskTable;

import java.sql.Connection;
import java.util.List;

/**
 * Adding class for adding new tasks
 */
public class Adding extends Application {
    private int loggedInUserId = 1;
    private Connection connection;

    /**
     * Setter for logged in user ID
     * @param userId userId
     */
    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }

    /**
     * Getter for logged in user ID
     */
    @Override
    public void start(Stage stage) {
        connection = Database.getInstance().getConnection();
        if (connection == null) {
            System.out.println("Connection is null in Adding class.");
            return;
        } else {
            System.out.println("Connection established in Adding class.");
        }

        Text taskLabel = new Text("Task");
        Text descriptionLabel = new Text("Description");
        Text dateLabel = new Text("Date");
        Text tagLabel = new Text("Tag");

        TextArea taskNameField = new TextArea();
        taskNameField.setPromptText("Add your new task here");
        taskNameField.setMaxSize(500, 50);

        TextArea taskDescriptionField = new TextArea();
        taskDescriptionField.setPromptText("Description");
        taskDescriptionField.setMaxSize(500, 150);

        DatePicker dueDatePicker = new DatePicker();

        ComboBox<String> tagsComboBox = new ComboBox<>();

        TagTable tagTable = new TagTable(connection);
        System.out.println("Fetching tags...");
        List<String> tags = tagTable.getAllTagTitles();
        System.out.println("Tags fetched: " + tags);
        if (!tags.isEmpty()) {
            tagsComboBox.getItems().addAll(tags);
        } else {
            System.out.println("No tags found in the database.");
        }

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #8cfa8c; -fx-font-size: 15; -fx-font-weight: bold;");

        submitButton.setOnAction(event -> {
            String taskName = taskNameField.getText().trim();
            String taskDescription = taskDescriptionField.getText().trim();
            String taskDate = dueDatePicker.getValue() != null ? dueDatePicker.getValue().toString() : null;
            String taskTag = tagsComboBox.getValue();

            if (taskName.isEmpty() || taskDescription.isEmpty() || taskDate == null || taskTag == null) {
                System.out.println("Please fill in all fields.");
                return;
            }

            Task task = new Task(taskName, taskDescription, false, false);
            task.setDueDate(taskDate);

            try {
                TaskTable taskTable = new TaskTable(connection);

                boolean isTaskCreated = taskTable.createTask(task, loggedInUserId);

                if (isTaskCreated) {
                    System.out.println("Task created with ID: " + task.getTaskId());

                    int tagId = tagTable.getTagIdByTitle(taskTag);
                    if (tagId > 0) {
                        boolean tagLinked = tagTable.linkTagToTask(task.getTaskId(), tagId);
                        if (tagLinked) {
                            System.out.println("Task added successfully with tag!");
                        } else {
                            System.out.println("Task added successfully, but failed to link tag.");
                        }
                    } else {
                        System.out.println("Tag not found in the database: " + taskTag);
                    }
                } else {
                    System.out.println("Failed to add task.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("An error occurred while adding the task.");
            }
        });

        VBox formBox = new VBox();
        formBox.getChildren().addAll(taskLabel, taskNameField, descriptionLabel, taskDescriptionField, dateLabel, dueDatePicker, tagLabel, tagsComboBox, submitButton);
        formBox.setAlignment(Pos.CENTER);
        formBox.setSpacing(10);

        BorderPane layout = new BorderPane();
        layout.setCenter(formBox);
        layout.setStyle("-fx-background-color: tan");

        Scene scene = new Scene(layout, 1000, 500);
        stage.setTitle("Adding New Task");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method for Adding
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }
}