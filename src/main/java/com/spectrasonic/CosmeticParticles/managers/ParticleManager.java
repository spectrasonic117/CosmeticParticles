package com.spectrasonic.CosmeticParticles.managers;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.cosmetics.BaseCosmetic;
import com.spectrasonic.CosmeticParticles.cosmetics.CosmeticType;
import com.spectrasonic.CosmeticParticles.cosmetics.ParticleCosmetic;
import com.spectrasonic.CosmeticParticles.cosmetics.TriadCosmetic;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Manages particle cosmetics for all players
 * Handles enabling, disabling, and updating particle effects
 */
@Getter
public class ParticleManager {
    
    private final Main plugin;
    private final Map<UUID, BaseCosmetic> activeCosmetics;
    private BukkitTask updateTask;
    private int tickCounter = 0;
    
    /**
     * Creates a new ParticleManager
     * 
     * @param plugin The main plugin instance
     */
    public ParticleManager(Main plugin) {
        this.plugin = plugin;
        this.activeCosmetics = new HashMap<>();
        startUpdateTask();
    }
    
    /**
     * Enables a cosmetic for a player
     * If the player already has a cosmetic, it will be replaced
     * 
     * @param player The player to enable the cosmetic for
     * @return true if the cosmetic was enabled successfully
     */
    public boolean enableCosmetic(Player player) {
        return enableCosmetic(player, CosmeticType.HELIX);
    }

    public boolean enableCosmetic(Player player, CosmeticType cosmeticType) {
        if (player == null || !player.isOnline()) {
            return false;
        }

        // Disable existing cosmetic if any
        disableCosmetic(player);

        // Create and start new cosmetic
        BaseCosmetic cosmetic = createCosmetic(player, cosmeticType);
        if (cosmetic == null) {
            return false;
        }

        cosmetic.start();
        activeCosmetics.put(player.getUniqueId(), cosmetic);
        return true;
    }

    private BaseCosmetic createCosmetic(Player player, CosmeticType cosmeticType) {
        return switch (cosmeticType) {
            case HELIX -> ParticleCosmetic.createMagicalCosmetic(player);
            case TRIAD -> TriadCosmetic.createTriadCosmetic(player);
        };
    }
    
    /**
     * Disables the cosmetic for a player
     * 
     * @param player The player to disable the cosmetic for
     * @return true if a cosmetic was disabled, false if no cosmetic was active
     */
    public boolean disableCosmetic(Player player) {
        if (player == null) {
            return false;
        }
        
        BaseCosmetic cosmetic = activeCosmetics.remove(player.getUniqueId());
        if (cosmetic != null) {
            cosmetic.stop();
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if a player has an active cosmetic
     * 
     * @param player The player to check
     * @return true if the player has an active cosmetic
     */
    public boolean hasActiveCosmetic(Player player) {
        if (player == null) {
            return false;
        }
        
        return activeCosmetics.containsKey(player.getUniqueId());
    }
    
    /**
     * Gets the active cosmetic for a player
     * 
     * @param player The player to get the cosmetic for
     * @return The player's active cosmetic, or null if none
     */
    public BaseCosmetic getCosmetic(Player player) {
        if (player == null) {
            return null;
        }
        
        return activeCosmetics.get(player.getUniqueId());
    }
    
    /**
     * Removes all active cosmetics
     * Should be called on plugin disable
     */
    public void disableAllCosmetics() {
        for (BaseCosmetic cosmetic : activeCosmetics.values()) {
            cosmetic.stop();
        }
        activeCosmetics.clear();
        
        if (updateTask != null) {
            updateTask.cancel();
            updateTask = null;
        }
    }
    
    /**
     * Removes cosmetics for offline players
     * Called periodically to clean up
     */
    public void cleanupOfflinePlayers() {
        activeCosmetics.entrySet().removeIf(entry -> {
            BaseCosmetic cosmetic = entry.getValue();
            if (!cosmetic.getPlayer().isOnline()) {
                cosmetic.stop();
                return true;
            }
            return false;
        });
    }
    
    /**
     * Starts the update task that animates all active cosmetics
     */
    private void startUpdateTask() {
        updateTask = new BukkitRunnable() {
            @Override
            public void run() {
                // Update all active cosmetics
                for (BaseCosmetic cosmetic : activeCosmetics.values()) {
                    cosmetic.update();
                }
                
                // Increment tick counter
                tickCounter++;
                
                // Clean up offline players every 100 ticks (5 seconds)
                if (tickCounter % 100 == 0) {
                    cleanupOfflinePlayers();
                }
            }
        }.runTaskTimer(plugin, 0L, 1L); // Run every tick for smooth animation
    }
    
    /**
     * Gets the number of active cosmetics
     * 
     * @return The number of players with active cosmetics
     */
    public int getActiveCosmeticCount() {
        return activeCosmetics.size();
    }
}