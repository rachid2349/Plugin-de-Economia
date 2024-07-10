package me.seuusuario.economiaplugin.listeners;

import me.seuusuario.economiaplugin.Main;
import me.seuusuario.economiaplugin.models.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private Main plugin;

    public PlayerJoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getDatabase().loadPlayerData(event.getPlayer().getUniqueId());
    }
}
