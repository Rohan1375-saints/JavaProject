package org.example.projectj3.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.projectj3.tables.TagTable;

import java.sql.Connection;
import java.util.List;

public class Dashboard extends Application {
    private int loggedInUserId;
    private PieChart chart;
    private String loggedInUser;

    /**
     * Setter for logged in user
     * @param username
     */
    public void setLoggedInUser(String username, int userId) {
        this.loggedInUser = username;
        this.loggedInUserId = userId;
    }
    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }

    /**
     * Getter for logged in user
     */
    @Override
    public void start(Stage stage) {
        chart = new PieChart();
        chart.setTitle("Tag Usage");
        chart.setLabelsVisible(true);
        generateChart();
        chart.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button backButton = new Button("Back to Main Page");
        backButton.setStyle("-fx-background-color: #f5c242; -fx-font-size: 15; -fx-font-weight: bold;");
        backButton.setOnAction(event -> {
            try {
                mainPage mainPageInstance = new mainPage();
                mainPageInstance.setLoggedInUser(loggedInUser);
                mainPageInstance.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Text updateUserLogin = new Text("Update User Login");
        Text user = new Text("Username");
        Text password = new Text("Password");
        TextArea log = new TextArea("Username");
        TextArea pass = new TextArea("Password");
        Button update = new Button("Update");

        updateUserLogin.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        user.setStyle("-fx-font-size: 21px; -fx-font-weight: bold;");
        password.setStyle("-fx-font-size: 21px; -fx-font-weight: bold;");
        log.setMaxSize(250, 25);
        pass.setMaxSize(250, 25);

        VBox vbox = new VBox(updateUserLogin, user, log, password, pass, update);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);

        BorderPane login = new BorderPane();
        login.setRight(vbox);

        HBox buttonBox = new HBox(backButton);
        buttonBox.setAlignment(Pos.CENTER);

        HBox center = new HBox(vbox, chart);
        center.setAlignment(Pos.CENTER);
        center.setSpacing(15);

        BorderPane layout = new BorderPane();
        layout.setCenter(center);
        layout.setBottom(buttonBox);
        layout.setStyle("-fx-background-color: TAN");

        Scene scene = new Scene(layout, 1000, 500);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();

        // NOTE: IT DOES NOT WORK
        update.setOnAction(event -> {
            String newUsername = log.getText().trim();
            String newPassword = pass.getText().trim();

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                System.out.println("Username or Password cannot be empty!");
                return;
            }

            try (Connection connection = org.example.projectj3.Database.Database.getInstance().getConnection()) {
                if (connection == null || connection.isClosed()) {
                    System.out.println("Database connection is closed or null!");
                    return;
                }

                org.example.projectj3.tables.UserTable userTable = new org.example.projectj3.tables.UserTable(connection);
                org.example.projectj3.pojo.User currentUser = userTable.getUserById(loggedInUserId);

                if (currentUser != null) {
                    currentUser.setUserName(newUsername);
                    currentUser.setPassword(newPassword);

                    boolean isUpdated = userTable.updateUser(currentUser);

                    if (isUpdated) {
                        System.out.println("Username and Password updated successfully!");
                        this.loggedInUser = newUsername;
                    } else {
                        System.out.println("Failed to update Username and Password.");
                    }
                } else {
                    System.out.println("User not found in the database!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Generate the chart for the dashboard
     */
    private void generateChart() {
        try (Connection connection = org.example.projectj3.Database.Database.getInstance().getConnection()) {
            if (connection == null || connection.isClosed()) {
                System.out.println("Database connection is closed or null!");
                return;
            }

            TagTable tagTable = new TagTable(connection);
            List<String> tags = tagTable.getAllTagTitles();
            ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList();

            for (String tag : tags) {
                int count = tagTable.getTagCountByUserId(loggedInUserId, tag);
                if (count > 0) {
                    chartData.add(new PieChart.Data(tag, count));
                }
            }

            chart.setData(chartData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
