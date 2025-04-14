package org.example.projectj3.GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ChoosePage class
 */
public class ChoosePage extends Application {

    /**
     * Start method for ChoosePage
     * @param stage stage
     */
    @Override
    public void start(Stage stage) {
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 16px; -fx-background-color: #8cfa8c; -fx-font-weight: bold;");

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-font-size: 16px; -fx-background-color: #8cfa8c; -fx-font-weight: bold;");

        loginButton.setOnAction(event -> {
            login loginPage = new login();
            try {
                loginPage.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        registerButton.setOnAction(event -> {
            register registerPage = new register();
            try {
                registerPage.start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(20, loginButton, registerButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: TAN; -fx-padding: 20;");

        Scene scene = new Scene(vbox, 400, 300);
        stage.setTitle("Choose Action");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method
     * @param args args
     */
    public static void main(String[] args) {
        launch();
    }
}
