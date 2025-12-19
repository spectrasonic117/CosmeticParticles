package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoubleHelixCosmetic implements BaseCosmetic {

    private final CosmeticType type = CosmeticType.DOUBLE_HELIX;

    private final Player player;
    private final Particle particleType = Particle.HAPPY_VILLAGER;
    private boolean active;
    
    // Configurable parameters
    private final double radius = 0.8;
    private final double height = 2.2;
    private final double verticalStep = 0.6; 
    private final double rotationSpeed = 0.13;
    private double currentRotation = 0.2;
    private final double helixFrequency = 2.0; // Fewer turns, more separated 

    public DoubleHelixCosmetic(Player player) {
        this.player = player;
        this.active = false;
    }

    @Override
    public void start() {
        if (active) return;
        active = true;
        currentRotation = 0.0;
    }

    @Override
    public void stop() {
        active = false;
    }

    @Override
    public void update() {
        if (!active || !player.isOnline()) {
            stop();
            return;
        }

        Location playerLoc = player.getLocation();
        
        // Draw the double helix
        // Draw the spring (single helix)
        for (double y = 0; y <= height; y += verticalStep) {
             double angle = (y * helixFrequency) + currentRotation;
             
             double x = Math.cos(angle) * radius;
             double z = Math.sin(angle) * radius;
             
             Location loc = playerLoc.clone().add(x, y, z);
             
             player.getWorld().spawnParticle(particleType, loc, 1, 0, 0, 0, 0);
        }

        // Update rotation for the next tick
        currentRotation += rotationSpeed;
        if (currentRotation > Math.PI * 2) {
            currentRotation -= Math.PI * 2;
        }
    }

    @Override
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }
}
