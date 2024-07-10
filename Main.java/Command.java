package me.seuusuario.economiaplugin.commands;

import me.seuusuario.economiaplugin.Main;
import me.seuusuario.economiaplugin.models.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomiaCommand implements CommandExecutor {

    private Main plugin;

    public EconomiaCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Apenas jogadores podem usar este comando!");
            return true;
        }

        Player player = (Player) sender;
        PlayerData playerData = plugin.getDatabase().getPlayerData(player.getUniqueId());

        if (args.length == 0) {
            player.sendMessage("Seu saldo é: " + playerData.getBalance());
            return true;
        }

        if (args[0].equalsIgnoreCase("pagar") && args.length == 3) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage("Jogador não encontrado!");
                return true;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                player.sendMessage("Quantia inválida!");
                return true;
            }

            if (playerData.getBalance() < amount) {
                player.sendMessage("Saldo insuficiente!");
                return true;
            }

            PlayerData targetData = plugin.getDatabase().getPlayerData(target.getUniqueId());
            playerData.setBalance(playerData.getBalance() - amount);
            targetData.setBalance(targetData.getBalance() + amount);

            plugin.getDatabase().updatePlayerData(playerData);
            plugin.getDatabase().updatePlayerData(targetData);

            player.sendMessage("Você pagou " + amount + " para " + target.getName());
            target.sendMessage("Você recebeu " + amount + " de " + player.getName());
            return true;
        }

        return false;
    }
}
