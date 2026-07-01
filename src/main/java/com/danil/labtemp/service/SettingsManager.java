package com.danil.labtemp.service;

import com.danil.labtemp.model.AppConfig;
import com.danil.labtemp.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {
    private static final String FILE_PATH = "labtemp_settings.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static AppConfig loadSettings() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return createDefaultConfig();
        }

        try (FileReader reader = new FileReader(file)) {

            AppConfig config = gson.fromJson(reader, AppConfig.class);

            if (config == null || config.users().isEmpty()) {
                config = new AppConfig(
                        config.companyName() == null ? "" : config.companyName(),
                        config.companyAddress() == null ? "" : config.companyAddress(),
                        config.devices() == null ? new ArrayList<>() : config.devices(),
                        List.of(new User("admin", "admin", "ADMIN", true))
                );
                saveSettings(config);
            }
            return config;

        } catch (IOException e) {
            e.printStackTrace();
            return createDefaultConfig();
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

    public static AppConfig createDefaultConfig() {
        return new AppConfig("", "", new ArrayList<>(), List.of(new User("admin", "admin", "ADMIN", true)));
    }
}
