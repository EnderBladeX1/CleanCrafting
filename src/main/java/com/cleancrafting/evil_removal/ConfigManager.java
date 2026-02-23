package com.cleancrafting.evil_removal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CONFIG_NAME = "evil_removal.json";

    public static EvilRemovalConfig load() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_NAME);
        if (!Files.exists(configPath)) {
            EvilRemovalConfig config = EvilRemovalConfig.createDefault();
            save(config, configPath);
            return config;
        }

        try (Reader reader = Files.newBufferedReader(configPath)) {
            EvilRemovalConfig config = GSON.fromJson(reader, EvilRemovalConfig.class);
            return config == null ? EvilRemovalConfig.createDefault() : config;
        } catch (IOException | JsonParseException e) {
            EvilRemovalConfig config = EvilRemovalConfig.createDefault();
            save(config, configPath);
            return config;
        }
    }

    private static void save(EvilRemovalConfig config, Path configPath) {
        try {
            Files.createDirectories(configPath.getParent());
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException ignored) {
        }
    }
}
