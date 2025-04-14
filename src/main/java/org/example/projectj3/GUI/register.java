package org.example.projectj3.GUI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.projectj3.Database.Database;
import org.example.projectj3.pojo.User;
import org.example.projectj3.tables.UserTable;

import java.sql.Connection;

public class register extends Application {

    private Stage primaryStage;

    /**
     * Start method for register
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Register Page");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: tan;");

        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;");
        grid.add(usernameLabel, 0, 0);

        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 0);

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;");
        grid.add(emailLabel, 0, 1);

        TextField emailField = new TextField();
        grid.add(emailField, 1, 1);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;");
        grid.add(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;");
        grid.add(confirmPasswordLabel, 0, 3);

        PasswordField confirmPasswordField = new PasswordField();
        grid.add(confirmPasswordField, 1, 3);

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #8cfa8c; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
        grid.add(registerButton, 1, 4);

        Label message = new Label();
        message.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(message, 1, 5);

        // Back Button
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f5c242; -fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(backButton, 1, 6);

        backButton.setOnAction(e -> {
            ChoosePage choosePage = new ChoosePage();
            try {
                choosePage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        registerButton.setOnAction(event -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                message.setText("All fields are required.");
                message.setTextFill(Color.RED);
                return;
            }

            if (!password.equals(confirmPassword)) {
                message.setText("Passwords do not match. Please try again.");
                message.setTextFill(Color.RED);
                return;
            }

            Connection connection = Database.getInstance().getConnection();
            if (connection == null) {
                message.setText("Database connection failed.");
                message.setTextFill(Color.RED);
                return;
            }

            UserTable userTable = new UserTable(connection);
            User user = new User(username, email, password, false);
            boolean result = userTable.createUser(user);

            if (result) {
                message.setText("Registration Successful!");
                message.setTextFill(Color.GREEN);
                switchToMainPage(username);
            } else {
                message.setText("Failed to register user. Email might already exist.");
                message.setTextFill(Color.RED);
            }

            Database.getInstance().closeConnection();
        });
        Scene scene = new Scene(grid, 400, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Switch to main page
     * @param username
     */
    private void switchToMainPage(String username) {
        mainPage mainPageInstance = new mainPage();
        mainPageInstance.setLoggedInUser(username);
        try {
            mainPageInstance.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
