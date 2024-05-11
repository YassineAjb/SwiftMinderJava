package org.example.Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;

public class MainFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream inputStream = getClass().getResourceAsStream("/Employee/Employe.fxml");
        if (inputStream == null) {
            System.err.println("Resource not found");
        } else {
            Parent root = loader.load(inputStream);
            Scene scene = new Scene(root, 900, 600);
            primaryStage.setTitle("Gérer personnes");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        }
    }
}