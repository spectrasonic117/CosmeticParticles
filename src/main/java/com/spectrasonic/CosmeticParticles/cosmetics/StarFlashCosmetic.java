package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import lombok.Getter;
import lombok.Setter;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
public class StarFlashCosmetic implements BaseCosmetic {

    private final CosmeticType type = CosmeticType.STAR_FLASH;
    private final Player player;
    private boolean active;
    
    // Configurable parameters
    private int particlesPerTick = 1;
    private double speed = 0.34;

    public StarFlashCosmetic(Player player) {
        this.player = player;
        this.active = false;
    }

    @Override
    public void start() {
        if (active) return;
        active = true;
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

        Location loc = player.getLocation().add(0, 1.0, 0); // Body center
        
        for (int i = 0; i < particlesPerTick; i++) {
            // Generate random direction
            double x = (ThreadLocalRandom.current().nextDouble() * 2) - 1;
            double y = (ThreadLocalRandom.current().nextDouble() * 2) - 1;
            double z = (ThreadLocalRandom.current().nextDouble() * 2) - 1;
            
            // Normalize to sphere
            Vector v = new Vector(x, y, z).normalize().multiply(0.5);

            // Use count=0 to use offset as direction
            player.getWorld().spawnParticle(
                Particle.END_ROD, 
                loc, 
                0, 
                v.getX(), 
                v.getY(), 
                v.getZ(), 
                speed
            );
        }
    }

    @Override
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }
}
