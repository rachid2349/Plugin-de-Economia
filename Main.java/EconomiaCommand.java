package me.seuusuario.economiaplugin;

import me.seuusuario.economiaplugin.commands.EconomiaCommand;
import me.seuusuario.economiaplugin.database.Database;
import me.seuusuario.economiaplugin.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Database database;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        database = new Database(this);
        getCommand("economia").setExecutor(new EconomiaCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getLogger().info("Plugin de Economia ativado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin de Economia desativado!");
    }

    public Database getDatabase() {
        return database;
    }
}





