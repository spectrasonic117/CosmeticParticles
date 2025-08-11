package com.spectrasonic.CosmeticParticles.managers;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.listeners.PlayerListener;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class EventManager {

    private final Main plugin;
    private PlayerListener playerListener;

    public EventManager(Main plugin) {
        this.plugin = plugin;
        registerEvents();
    }

    private void registerEvents() {
        // Register the player listener for cosmetic cleanup
        this.playerListener = new PlayerListener(plugin, plugin.getParticleManager());
        Bukkit.getPluginManager().registerEvents(playerListener, plugin);
    }

}