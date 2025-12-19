package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PulsatingRingCosmetic implements BaseCosmetic {

    private final CosmeticType type = CosmeticType.PULSATING_RING;
    private final Player player;
    private final Particle particleType = Particle.DUST;
    private boolean active = false;

    // Config
    private final double footOffset = 0.2;
    private final double baseRadius = 1.2;
    private final double minScale = 0.65;
    private final Color color = Color.fromRGB(213, 90, 182);
    private final Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0f);
    
    // State
    private double time = 0.0;
    private final double speed = 0.1; // Speed of pulsation

    public PulsatingRingCosmetic(Player player) {
        this.player = player;
    }

    @Override
    public void start() {
        active = true;
        time = 0.0;
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

        Location loc = player.getLocation();
        
        // Calculate pulsation
        // Math.sin oscillates between -1 and 1
        // We map this to 0 to 1 range: (sin + 1) / 2
        double sineValue = (Math.sin(time) + 1) / 2; 
        
        // Interpolate between minScale and 1.0
        double currentScale = minScale + (1.0 - minScale) * sineValue;
        double currentRadius = baseRadius * currentScale;

        // Draw ring
        int points = 30; // Density of the ring
        double increment = (2 * Math.PI) / points;

        for (int i = 0; i < points; i++) {
            double angle = i * increment;
            double x = loc.getX() + (Math.cos(angle) * currentRadius);
            double z = loc.getZ() + (Math.sin(angle) * currentRadius);
            double y = loc.getY() + footOffset; // At feet level as requested

            player.getWorld().spawnParticle(
                particleType,
                x, y, z,
                1,
                0, 0, 0,
                0,
                dustOptions
            );
        }

        time += speed;
    }

    @Override
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }
}
