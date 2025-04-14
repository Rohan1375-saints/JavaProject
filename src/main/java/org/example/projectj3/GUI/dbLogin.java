package org.example.projectj3.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.projectj3.Database.Database;

public class dbLogin extends Application {

    /**
     * Start method for dbLogin
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Database Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Database Password");

        TextField dbNameField = new TextField();
        dbNameField.setPromptText("Database Name");

        Button submitButton = new Button("Connect");
        Label messageLabel = new Label();

        submitButton.setOnAction(e -> {
            String dbName = dbNameField.getText().trim();
            String dbUser = usernameField.getText().trim();
            String dbPassword = passwordField.getText().trim();

            if (dbName.isEmpty() || dbUser.isEmpty() || dbPassword.isEmpty()) {
                messageLabel.setText("Please fill all fields.");
                return;
            }

            boolean isConnected = Database.getInstance().initializeConnection(dbName, dbUser, dbPassword);

            if (isConnected) {
                messageLabel.setText("Connected to the database!");

                ChoosePage choosePage = new ChoosePage();
                try {
                    choosePage.start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                messageLabel.setText("Failed to connect. Check credentials.");
            }
        });

        VBox vbox = new VBox(10, dbNameField, usernameField, passwordField, submitButton, messageLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20; -fx-background-color: TAN;");

        Scene scene = new Scene(new BorderPane(vbox), 400, 300);
        stage.setTitle("Database Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
