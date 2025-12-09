package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelixCosmetic implements BaseCosmetic {

    private final CosmeticType type = CosmeticType.HELIX;

    private final Player player;
    private final Particle particleType = Particle.DUST;
    private final int particleCount = 3;
    private final double radius = 1.5;
    private final double speed = 0.2;
    private final double yOffset = 0.2;

    private BukkitTask animationTask;
    private double angle;
    private boolean active;

    public HelixCosmetic(Player player) {
        this.player = player;
        this.angle = 0.0;
        this.active = false;
    }
    

    
    public void start() {
        if (active || animationTask != null) {
            return;
        }
        
        active = true;
        // Reset angle when starting
        angle = 0.0;
    }
    
    public void stop() {
        active = false;
        if (animationTask != null) {
            animationTask.cancel();
            animationTask = null;
        }
    }
    
    public void update() {
        if (!active || !player.isOnline()) {
            stop();
            return;
        }
        
        Location playerLocation = player.getLocation();
        
        // Orbiting particles (Dust)
        Particle.DustOptions dustOptions = new Particle.DustOptions(
            org.bukkit.Color.fromRGB(15, 241, 241), 
            1.0f
        );
        
        for (int i = 0; i < 2; i++) {
            double particleAngle = angle + (i * Math.PI); // Two opposite particles
            
            double x = playerLocation.getX() + Math.cos(particleAngle) * radius;
            double y = playerLocation.getY() + yOffset;
            double z = playerLocation.getZ() + Math.sin(particleAngle) * radius;
            
            Location particleLocation = new Location(playerLocation.getWorld(), x, y, z);
            
            playerLocation.getWorld().spawnParticle(
                Particle.DUST,
                particleLocation,
                1,
                dustOptions
            );
        }
        
        // Burst particles (Soul Fire Flame)
        if (Math.random() < 0.1) { // 10% chance per tick
            Location center = player.getLocation().add(0, 1, 0); // Player's center
            for (int i = 0; i < 5; i++) {
                double offsetX = (Math.random() - 0.5) * 2;
                double offsetY = (Math.random() - 0.5) * 2;
                double offsetZ = (Math.random() - 0.5) * 2;
                
                player.getWorld().spawnParticle(
                    Particle.SOUL_FIRE_FLAME,
                    center,
                    0,
                    offsetX,
                    offsetY,
                    offsetZ,
                    0.1 // Speed
                );
            }
        }
        
        // Increment angle for smooth rotation
        angle += speed;
        if (angle >= 2 * Math.PI) {
            angle = 0.0; // Reset to prevent overflow
        }
    }
    
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }
}