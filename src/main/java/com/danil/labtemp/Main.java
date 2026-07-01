package com.danil.labtemp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Promenjeno da učitava Login.fxml umesto dashboard.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view/admin-panel.fxml"));

        // Login prozor je manji (350x400)
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        primaryStage.setTitle("LabTemp - Prijava na sistem");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Login prozor ne treba da menja veličinu

        // Pravilno gašenje na "X"
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}