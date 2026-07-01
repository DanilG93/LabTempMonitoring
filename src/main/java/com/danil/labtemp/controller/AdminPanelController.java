package com.danil.labtemp.controller;

import com.danil.labtemp.model.AppConfig;
import com.danil.labtemp.model.Device;
import com.danil.labtemp.service.SettingsManager;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AdminPanelController {

    // --- Tab 1: Podaci o firmi ---
    @FXML
    private TextField companyNameField;
    @FXML
    private TextField companyAddressField;

    // --- Tab 2: Tabela aparata ---
    @FXML
    private TableView<Device> deviceTable;
    @FXML
    private TableColumn<Device, String> nameCol;
    @FXML
    private TableColumn<Device, Number> minCol;
    @FXML
    private TableColumn<Device, Number> maxCol;
    @FXML
    private TableColumn<Device, String> regexCol;

    // --- Tab 2: Forma za dodavanje ---
    @FXML
    private TextField deviceNameField;
    @FXML
    private TextField minTempField;
    @FXML
    private TextField maxTempField;
    @FXML
    private TextField dataRegexField;
    @FXML
    private TextField alarmRegexField;

    // Lista koja automatski osvežava tabelu kada se doda novi aparat
    private ObservableList<Device> deviceList = FXCollections.observableArrayList();
    private AppConfig currentConfig;


    @FXML
    public void initialize() {
        // Povezivanje kolona tabele sa podacima iz Device Record-a
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().name()));
        minCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().minTemp()));
        maxCol.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().maxTemp()));
        regexCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().dataRegex()));

        currentConfig = SettingsManager.loadSettings();

        companyNameField.setText(currentConfig.companyName());
        companyAddressField.setText(currentConfig.companyAddress());

        deviceList.addAll(currentConfig.devices());
        deviceTable.setItems(deviceList);
    }

    @FXML
    private void handleSaveCompany() {
        String name = companyNameField.getText();
        String address = companyAddressField.getText();

        currentConfig = new AppConfig(name, address, new ArrayList<>(deviceList));
        SettingsManager.saveSettings(currentConfig);

        showAlert("Uspešno", "Podaci o firmi su uspešno sačuvani u fajl!");
    }

    @FXML
    private void handleAddDevice() {
        try {
            String name = deviceNameField.getText();
            double min = Double.parseDouble(minTempField.getText());
            double max = Double.parseDouble(maxTempField.getText());
            String dataRegex = dataRegexField.getText();
            String alarmRegex = alarmRegexField.getText();

            Device newDevice = new Device(name, min, max, dataRegex, alarmRegex);
            deviceList.add(newDevice);

            currentConfig = new AppConfig(companyNameField.getText(), companyAddressField.getText(), new ArrayList<>(deviceList));
            SettingsManager.saveSettings(currentConfig);

            deviceNameField.clear();
            minTempField.clear();
            maxTempField.clear();
            dataRegexField.clear();
            alarmRegexField.clear();

        } catch (NumberFormatException e) {
            showAlert("Greška", "Min i Max temperatura moraju biti brojevi!");
        }
    }

    @FXML
    private void handleDeleteDevice() {
        Device selected = deviceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            deviceList.remove(selected);

            currentConfig = new AppConfig(companyNameField.getText(), companyAddressField.getText(), new ArrayList<>(deviceList));
            SettingsManager.saveSettings(currentConfig);
        } else {
            showAlert("Upozorenje", "Izaberite aparat iz tabele za brisanje.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}