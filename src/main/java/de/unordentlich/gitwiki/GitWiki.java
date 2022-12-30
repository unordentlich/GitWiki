package de.unordentlich.gitwiki;

import de.unordentlich.gitwiki.commands.Wiki;
import de.unordentlich.gitwiki.utils.ConfigHandler;
import de.unordentlich.gitwiki.utils.RepositoryScan;
import de.unordentlich.gitwiki.utils.Variables;
import de.unordentlich.gitwiki.utils.objects.TopicView;
import kong.unirest.UnirestException;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class GitWiki extends JavaPlugin {

    private static GitWiki instance;
    private static ConfigHandler configHandler;

    @Override
    public void onEnable() {
        instance = this;

        configHandler = new ConfigHandler();
        configHandler.setup();

        Objects.requireNonNull(getCommand("wiki")).setExecutor(new Wiki());

        initialize();
    }

    @Override
    public void onDisable() {

    }

    private void initialize() {
        Variables.owner = configHandler.config.getString("github.user");
        Variables.repo = configHandler.config.getString("github.repository");
        Variables.permissionRequired = configHandler.config.getBoolean("permissionRequired");

        try {
            Variables.viewType = TopicView.valueOf(configHandler.config.getString("printType"));
        } catch (IllegalArgumentException e) {
            Variables.viewType = TopicView.CHAT;
            Bukkit.getConsoleSender().sendMessage("§c§lGitWiki §7§l> §c§l" + configHandler.config.getString("printType") + "§7§l is not a valid print type! (BOOK, CHAT)");
        }

        try {
            RepositoryScan.analyze("");
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    public static GitWiki getInstance() {
        return instance;
    }
}
