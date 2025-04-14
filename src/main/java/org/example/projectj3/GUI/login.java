package org.example.projectj3.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.projectj3.Database.Database;
import org.example.projectj3.tables.UserTable;

import java.sql.Connection;

public class login extends Application {

    private Stage primaryStage;

    /**
     * Start method for login
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: tan;");

        Label userName = new Label("Username:");
        userName.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;");
        grid.add(userName, 0, 1);

        TextField usernameEntered = new TextField();
        grid.add(usernameEntered, 1, 1);

        Label passwordEntered = new Label("Password:");
        passwordEntered.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-font-size: 14px;");
        grid.add(passwordEntered, 0, 2);

        PasswordField passwordTextfield = new PasswordField();
        grid.add(passwordTextfield, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #8cfa8c; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
        grid.add(loginButton, 1, 3);

        Label message = new Label();
        message.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(message, 1, 4);
        message.setTextFill(Color.RED);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f5c242; -fx-font-weight: bold; -fx-font-size: 14px;");
        grid.add(backButton, 1, 5);

        backButton.setOnAction(e -> {
            ChoosePage choosePage = new ChoosePage();
            try {
                choosePage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        loginButton.setOnAction(e -> {
            String username = usernameEntered.getText();
            String password = passwordTextfield.getText();

            if (username.isEmpty() || password.isEmpty()) {
                message.setText("Please enter both username and password.");
                message.setTextFill(Color.RED);
                return;
            }

            Connection connection = Database.getInstance().getConnection(); // Automatically reconnects if closed
            if (connection == null) {
                message.setText("Database connection failed.");
                message.setTextFill(Color.RED);
                return;
            }

            UserTable userTable = new UserTable(connection);
            boolean isAuthenticated = userTable.authenticateUser(username, password);

            if (isAuthenticated) {
                message.setText("Login successful!");
                message.setTextFill(Color.GREEN);
                switchToMainPage(username);
            } else {
                message.setText("Invalid username or password.");
                message.setTextFill(Color.RED);
            }
            Database.getInstance().closeConnection();
        });

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setTitle("Login Page");
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
