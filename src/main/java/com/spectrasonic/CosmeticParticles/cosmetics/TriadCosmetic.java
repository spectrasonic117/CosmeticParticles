package com.spectrasonic.CosmeticParticles.cosmetics;

import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
@SuppressWarnings("deprecation")
public class TriadCosmetic implements BaseCosmetic {

    private final CosmeticType type = CosmeticType.TRIAD;
    private final Player player;
    private final int particleCount = 3;
    private final double radius = 1.2;
    private final double speed = 0.15;
    private final double yOffset = 0.5;
    private double angle = 0.0;
    private boolean active;
    private BukkitTask animationTask;

    public static TriadCosmetic createTriadCosmetic(Player player) {
        return new TriadCosmetic(player);
    }

    @Override
    public void start() {
        if (active || animationTask != null) {
            return;
        }
        active = true;
        angle = 0.0;
        animationTask = player.getServer().getScheduler().runTaskTimer(
                player.getServer().getPluginManager().getPlugin("CosmeticParticles"),
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        update();
                    }
                },
                0L,
                2L);
    }

    @Override
    public void stop() {
        active = false;
        if (animationTask != null) {
            animationTask.cancel();
            animationTask = null;
        }
    }

    @Override
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }

    @Override
    public CosmeticType getType() {
        return type;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        if (!active || !player.isOnline()) {
            stop();
            return;
        }

        Location playerLocation = player.getLocation();

        // Crear efecto de triada ascendente con TRIAL_SPAWNER_DETECTION_OMINOUS
        for (int i = 0; i < particleCount; i++) {
            double triadAngle = angle + (i * (2 * Math.PI / particleCount));

            // Calcular posición con movimiento ascendente
            double x = playerLocation.getX() + Math.cos(triadAngle) * radius;
            double y = playerLocation.getY() + yOffset + (Math.sin(angle * 2) * 0.3);
            double z = playerLocation.getZ() + Math.sin(triadAngle) * radius;

            Location particleLocation = new Location(playerLocation.getWorld(), x, y, z);

            // Spawnear partícula con efecto ominoso
            playerLocation.getWorld().spawnParticle(
                    Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,
                    particleLocation,
                    1,
                    0.0, 0.0, 0.0, 0.02);
        }

        // Agregar partículas adicionales para efecto más dramático
        if (Math.random() < 0.3) {
            double centerX = playerLocation.getX();
            double centerY = playerLocation.getY() + yOffset + 1.0;
            double centerZ = playerLocation.getZ();

            // Crear partículas centrales ascendentes
            for (int i = 0; i < 2; i++) {
                double offsetY = i * 0.5;
                Location centerLocation = new Location(
                        playerLocation.getWorld(),
                        centerX + (Math.random() - 0.5) * 0.5,
                        centerY + offsetY,
                        centerZ + (Math.random() - 0.5) * 0.5);

                playerLocation.getWorld().spawnParticle(
                        Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,
                        centerLocation,
                        1,
                        0.0, 0.0, 0.0, 0.01);
            }
        }

        // Incrementar ángulo para rotación
        angle += speed;
        if (angle >= 2 * Math.PI) {
            angle = 0.0;
        }
    }
}