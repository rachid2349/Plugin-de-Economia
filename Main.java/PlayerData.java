package me.seuusuario.economiaplugin.models;

import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private double balance;

    public PlayerData(UUID uuid, double balance) {
        this.uuid = uuid;
        this.balance = balance;
    }

    public UUID getUuid() {
        return uuid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
