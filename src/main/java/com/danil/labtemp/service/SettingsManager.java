package com.danil.labtemp.service;

import com.danil.labtemp.model.AppConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsManager {
    private static final String FILE_PATH = "labtemp_settings.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static AppConfig loadSettings() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new AppConfig("", "", new ArrayList<>());
        }

        try (FileReader reader = new FileReader(file)) {

            return gson.fromJson(reader, AppConfig.class);

        } catch (IOException e) {
            e.printStackTrace();
            return new AppConfig("Greška", "Greška pri učitavanju", new ArrayList<>());
        }
    }

    public static void saveSettings(AppConfig config) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(config, writer);
            System.out.println("Podešavanja uspešno sačuvana u: " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
