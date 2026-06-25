package com.danil.labtemp.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        errorLabel.setText(""); // Resetujemo poruku o grešci na startu
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Jednostavna provera za početak (kasnije može ići preko baze/fajla)
        if ("admin".equals(username) && "admin".equals(password)) {
            try {
                // 1. Učitaj Dashboard FXML
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/danil/labtemp/view/dashboard.fxml"));

                // Dobavi trenutni Stage (prozor) preko bilo koje komponente sa ekrana
                Stage currentStage = (Stage) usernameField.getScene().getWindow();

                // 2. Postavi novu scenu (Dashboard) sa istom rezolucijom
                Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
                currentStage.setScene(scene);
                currentStage.setTitle("LabTemp Monitoring System - Dashboard");
                currentStage.centerOnScreen(); // Centriraj novi veći prozor

            } catch (IOException e) {
                e.printStackTrace();
                errorLabel.setText("Greška pri učitavanju glavnog ekrana!");
            }
        } else {
            errorLabel.setText("Pogrešno korisničko ime ili lozinka!");
            passwordField.clear();
        }
    }
}