package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.entity.Player;

public interface BaseCosmetic {
    void start();
    void stop();
    void update();
    boolean belongsTo(Player player);
    CosmeticType getType();
    Player getPlayer();
}