package me.seuusuario.economiaplugin.database;

import me.seuusuario.economiaplugin.Main;
import me.seuusuario.economiaplugin.models.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Database {

    private Main plugin;
    private Connection connection;
    private Map<UUID, PlayerData> playerDataMap;

    public Database(Main plugin) {
        this.plugin = plugin;
        playerDataMap = new HashMap<>();
        connect();
    }

    private void connect() {
        FileConfiguration config = plugin.getConfig();
        String host = config.getString("database.host");
        int port = config.getInt("database.port");
        String database = config.getString("database.name");
        String user = config.getString("database.user");
        String password = config.getString("database.password");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
            plugin.getLogger().info("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().severe("Não foi possível conectar ao banco de dados!");
        }
    }

    public void loadPlayerData(UUID uuid) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM players WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                double balance = rs.getDouble("balance");
                playerDataMap.put(uuid, new PlayerData(uuid, balance));
            } else {
                playerDataMap.put(uuid, new PlayerData(uuid, 0.0));
                PreparedStatement insert = connection.prepareStatement("INSERT INTO players (uuid, balance) VALUES (?, ?)");
                insert.setString(1, uuid.toString());
                insert.setDouble(2, 0.0);
                insert.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.get(uuid);
    }

    public void updatePlayerData(PlayerData playerData) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE players SET balance = ? WHERE uuid = ?");
            ps.setDouble(1, playerData.getBalance());
            ps.setString(2, playerData.getUuid().toString());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
