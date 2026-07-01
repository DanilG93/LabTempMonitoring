package com.danil.labtemp.controller;

import com.danil.labtemp.model.AppConfig;
import com.danil.labtemp.model.User;
import com.danil.labtemp.service.SessionManager;
import com.danil.labtemp.service.SettingsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        errorLabel.setText(""); // Resetujemo poruku o grešci na startu
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        AppConfig config = SettingsManager.loadSettings();
        User loggedUser = null;

        for (User user : config.users()) {
            if (user.username().equals(username) && user.password().equals(password)) {
                loggedUser = user;
                break;
            }
        }

        if (loggedUser != null) {
            if (loggedUser.requirePasswordChange()) {
                boolean changed = forcePasswordChange(loggedUser, config);
                if (!changed) {
                    errorLabel.setText("Morate promeniti lozinku da biste nastavili!");
                    return;
                }
            } else {
                SessionManager.setCurrentUser(loggedUser);
            }
            loadDashboard();
        } else {
            errorLabel.setText("Pogrešno korisničko ime ili lozinka!");
            passwordField.clear();
        }

    }

    private boolean forcePasswordChange(User oldUser, AppConfig config) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Obavezna promena lozinke");
        dialog.setHeaderText("Dobrodošli, " + oldUser.username() + "!\nAdministrator je zahtevao da promenite lozinku.");
        dialog.setContentText("Unesite novu lozinku:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty()) {
            String newPassword = result.get().trim();

            User updatedUser = new User(oldUser.username(), newPassword, oldUser.role(), false);

            List<User> safeUserList = new ArrayList<>();
            for (User u : config.users()) {
                if (u.username().equals(oldUser.username())) {
                    safeUserList.add(updatedUser);
                } else {
                    safeUserList.add(u);
                }
            }

            AppConfig newConfig = new AppConfig(config.companyName(), config.companyAddress(), config.devices(), safeUserList);
            SettingsManager.saveSettings(newConfig);

            SessionManager.setCurrentUser(updatedUser);
            return true;
        }
        return false;
    }

    private void loadDashboard() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/danil/labtemp/view/dashboard.fxml"));
            Stage currentStage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load(), 1024, 768);
            currentStage.setScene(scene);

            User user = SessionManager.getCurrentUser();
            currentStage.setTitle("LabTemp Dashboard - Prijavljen kao: " + user.username() + " [" + user.role() + "]");
            currentStage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Greška pri učitavanju glavnog ekrana!");
        }
    }
}