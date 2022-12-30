package de.unordentlich.gitwiki.utils;


import de.unordentlich.gitwiki.GitWiki;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {
    public final File file = new File(GitWiki.getInstance().getDataFolder().getPath(), "config.yml");
    public YamlConfiguration config = YamlConfiguration.loadConfiguration(this.file);

    public ConfigHandler() {
        createIfNotExists();
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        update();
    }

    public void load() {
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void createIfNotExists() {
        File file = new File(GitWiki.getInstance().getDataFolder().getPath(), "config.yml");
        if (!file.exists()) {
            file = new File(GitWiki.getInstance().getDataFolder().getPath(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(file);


        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        load();
    }

    public void setup() {
        load();

        config.addDefault("github.user", "unordentlich");
        config.addDefault("github.repository", "GitWiki");
        config.addDefault("printType", "CHAT");

        config.options().copyDefaults(true);
        save();
    }

    public YamlConfiguration getConfig() {
        update();
        return config;
    }
}