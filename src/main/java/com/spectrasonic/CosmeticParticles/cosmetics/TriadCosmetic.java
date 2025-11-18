package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TriadCosmetic implements BaseCosmetic {

    private final Player player;
    private final CosmeticType type = CosmeticType.TRIAD;
    private final int particleCount;
    private final double radius;
    private final double speed;
    private final double yOffset;

    private BukkitTask animationTask;
    private double angle;
    private boolean active;

    public TriadCosmetic(Player player, int particleCount, double radius, double speed, double yOffset) {
        this.player = player;
        this.particleCount = particleCount;
        this.radius = radius;
        this.speed = speed;
        this.yOffset = yOffset;
        this.angle = 0.0;
        this.active = false;
    }

    public static TriadCosmetic createTriadCosmetic(Player player) {
        return new TriadCosmetic(
            player,
            3, // 3 partículas para formar la triada
            1.2, // Radio más pequeño para efecto compacto
            0.15, // Velocidad moderada
            0.5 // Offset más alto para efecto ascendente
        );
    }

    @Override
    public void start() {
        if (active || animationTask != null) {
            return;
        }

        active = true;
        angle = 0.0;
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
    public void update() {
        if (!active || !player.isOnline()) {
            stop();
            return;
        }

        Location playerLocation = player.getLocation();

        // Crear efecto de triada ascendente con TRIAL_SPAWNER_DETECTION_OMINOUS
        for (int i = 0; i < 3; i++) {
            double triadAngle = angle + (i * (2 * Math.PI / 3)); // 120 grados entre cada partícula

            // Calcular posición con movimiento ascendente
            double x = playerLocation.getX() + Math.cos(triadAngle) * radius;
            double y = playerLocation.getY() + yOffset + (Math.sin(angle * 2) * 0.3); // Movimiento vertical
            double z = playerLocation.getZ() + Math.sin(triadAngle) * radius;

            Location particleLocation = new Location(playerLocation.getWorld(), x, y, z);

            // Spawnear partícula con efecto ominoso
            playerLocation.getWorld().spawnParticle(
                Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,
                particleLocation,
                1,
                0.0, // Sin dispersión horizontal
                0.0, // Sin dispersión vertical
                0.0, // Sin dispersión en Z
                0.02 // Velocidad baja para efecto misterioso
            );
        }

        // Agregar partículas adicionales para efecto más dramático
        if (Math.random() < 0.3) { // 30% de probabilidad
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
                    centerZ + (Math.random() - 0.5) * 0.5
                );

                playerLocation.getWorld().spawnParticle(
                    Particle.TRIAL_SPAWNER_DETECTION_OMINOUS,
                    centerLocation,
                    1,
                    0.0, 0.0, 0.0, 0.01
                );
            }
        }

        // Incrementar ángulo para rotación
        angle += speed;
        if (angle >= 2 * Math.PI) {
            angle = 0.0;
        }
    }

    @Override
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }
}