package com.danil.labtemp.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class DashboardController {

    @FXML
    private LineChart<String, Number> temperatureChart;
    @FXML private TableView<?> measurementTable;
    @FXML private Label currentTempLabel;
    @FXML private Label statusLabel;
    @FXML private Button importButton;

    @FXML
    public void initialize() {
        System.out.println("UI Učitan: DashboardController je aktivan!");
        statusLabel.setText("Status: Sistem spreman");
    }

    @FXML
    private void handleImport() {
        System.out.println("Kliknuto dugme za uvoz podataka!");
        statusLabel.setText("Status: Učitavanje...");
    }
}
