package com.spectrasonic.CosmeticParticles.listeners;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;

import lombok.Getter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Getter
public class PlayerListener implements Listener {
    
    private final Main plugin;
    private final ParticleManager particleManager;
    
    public PlayerListener(Main plugin, ParticleManager particleManager) {
        this.plugin = plugin;
        this.particleManager = particleManager;
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        particleManager.disableCosmetic(event.getPlayer());
    }
}