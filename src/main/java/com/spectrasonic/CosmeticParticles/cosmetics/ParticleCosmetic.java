package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a cosmetic particle effect for a player
 * Handles the orbital particle animation around the player
 */
@Getter
@Setter
public class ParticleCosmetic {
    
    private final Player player;
    private final Particle particleType;
    private final int particleCount;
    private final double radius;
    private final double speed;
    private final double yOffset;
    
    private BukkitTask animationTask;
    private double angle;
    private boolean active;
    
    /**
     * Creates a new particle cosmetic for a player
     * 
     * @param player The player to apply the cosmetic to
     * @param particleType The type of particle to display
     * @param particleCount Number of particles per animation frame
     * @param radius Orbital radius around the player
     * @param speed Animation speed (angle increment per tick)
     * @param yOffset Y offset from player's feet
     */
    public ParticleCosmetic(Player player, Particle particleType, int particleCount, 
                           double radius, double speed, double yOffset) {
        this.player = player;
        this.particleType = particleType;
        this.particleCount = particleCount;
        this.radius = radius;
        this.speed = speed;
        this.yOffset = yOffset;
        this.angle = 0.0;
        this.active = false;
    }
    
    /**
     * Creates a default magical particle cosmetic
     * 
     * @param player The player to apply the cosmetic to
     * @return A new ParticleCosmetic with magical settings
     */
    public static ParticleCosmetic createMagicalCosmetic(Player player) {
        return new ParticleCosmetic(
            player,
            Particle.END_ROD, // Magical particles
            3, // 3 particles per frame
            1.5, // 1.5 block radius
            0.1, // Smooth rotation speed
            0.2 // Slightly above feet
        );
    }
    
    /**
     * Starts the particle animation
     */
    public void start() {
        if (active || animationTask != null) {
            return;
        }
        
        active = true;
        // Reset angle when starting
        angle = 0.0;
    }
    
    /**
     * Stops the particle animation
     */
    public void stop() {
        active = false;
        if (animationTask != null) {
            animationTask.cancel();
            animationTask = null;
        }
    }
    
    /**
     * Updates the particle animation for one frame
     * This method should be called periodically to animate the particles
     */
    public void update() {
        if (!active || !player.isOnline()) {
            stop();
            return;
        }
        
        Location playerLocation = player.getLocation();
        
        // Calculate multiple particle positions in a circle
        for (int i = 0; i < particleCount; i++) {
            double particleAngle = angle + (i * (2 * Math.PI / particleCount));
            
            double x = playerLocation.getX() + Math.cos(particleAngle) * radius;
            double y = playerLocation.getY() + yOffset;
            double z = playerLocation.getZ() + Math.sin(particleAngle) * radius;
            
            Location particleLocation = new Location(playerLocation.getWorld(), x, y, z);
            
            // Spawn the particle for all nearby players
            playerLocation.getWorld().spawnParticle(
                particleType,
                particleLocation,
                1, // Count per spawn
                0.0, // No offset X
                0.0, // No offset Y  
                0.0, // No offset Z
                0.0  // No extra data
            );
        }
        
        // Increment angle for smooth rotation
        angle += speed;
        if (angle >= 2 * Math.PI) {
            angle = 0.0; // Reset to prevent overflow
        }
    }
    
    /**
     * Checks if this cosmetic belongs to the specified player
     * 
     * @param player The player to check
     * @return true if this cosmetic belongs to the player
     */
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }
}