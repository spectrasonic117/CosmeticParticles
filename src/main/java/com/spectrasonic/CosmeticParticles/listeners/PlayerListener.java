package com.spectrasonic.CosmeticParticles.listeners;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Handles player-related events for cosmetic particles
 * Ensures proper cleanup when players disconnect
 */
public class PlayerListener implements Listener {
    
    private final Main plugin;
    private final ParticleManager particleManager;
    
    /**
     * Creates a new PlayerListener
     * 
     * @param plugin The main plugin instance
     * @param particleManager The particle manager instance
     */
    public PlayerListener(Main plugin, ParticleManager particleManager) {
        this.plugin = plugin;
        this.particleManager = particleManager;
    }
    
    /**
     * Handles player quit events
     * Automatically disables cosmetics when a player leaves
     * 
     * @param event The player quit event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Clean up the player's cosmetic when they leave
        // This prevents memory leaks and ensures proper cleanup
        particleManager.disableCosmetic(event.getPlayer());
    }
}