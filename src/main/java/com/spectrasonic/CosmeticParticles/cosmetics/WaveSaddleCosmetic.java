package com.spectrasonic.CosmeticParticles.cosmetics;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WaveSaddleCosmetic implements BaseCosmetic {

    private static final double TWO_PI = 2 * Math.PI;

    private final CosmeticType type = CosmeticType.WAVE_SADDLE;
    private final Player player;
    private final Particle particleType = Particle.END_ROD;

    // Configuración
    private final double radius = 1.0;
    private final double waveAmplitude = 0.25;
    private final double layerSeparation = 0.1;
    private final double heightOffset = 0.8;

    // Ajustes de animación
    private final double speed = 0.04;      // Velocidad de rotación horaria
    private final double slantOffset = 2; // Desfase angular para el efecto inclinado (adelantado/atrasado)
    
    // Configuración de estela (Shooting Star)
    private final double trailLength = 0.8;
    private final double trailGap = 0.3;

    // Estado
    private double angle;
    private boolean active;

    public WaveSaddleCosmetic(Player player) {
        this.player = player;
        this.angle = 0.0;
        this.active = false;
    }

    @Override
    public void start() {
        if (active) return;
        active = true;
        angle = 0.0;
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
        double baseX = loc.getX();
        double baseY = loc.getY() + heightOffset;
        double baseZ = loc.getZ();

        // Generar 2 grupos de estrellas (desfasados 180 grados)
        for (int i = 0; i < 2; i++) {
            double groupAngle = angle + (i * Math.PI);

            // Capa SUPERIOR: Adelantada (+slantOffset)
            renderStar(baseX, baseY, baseZ, groupAngle + slantOffset, layerSeparation, loc);

            // Capa MEDIA: Centro (groupAngle)
            renderStar(baseX, baseY, baseZ, groupAngle, 0.0, loc);

            // Capa INFERIOR: Atrasada (-slantOffset)
            renderStar(baseX, baseY, baseZ, groupAngle - slantOffset, -layerSeparation, loc);
        }

        // Rotación Horaria (aumentamos el ángulo, en Minecraft esto rota Clockwise visualmente desde X a Z)
        angle += speed;
        if (angle >= TWO_PI) {
            angle -= TWO_PI;
        }
    }

    /**
     * Renderiza una "estrella" con estela en la posición calculada
     */
    private void renderStar(double cx, double cy, double cz, double baseAngle, double yLayerOffset, Location world) {
        // Renderizar la estela (cola) hacia atrás
        for (int i = 0; i < trailLength; i++) {
            // Cada punto de la cola está un poco más "atrás" en el ángulo
            double currentAngle = baseAngle - (i * trailGap);
            
            // Coordenadas circulares X/Z
            // Mantenemos radio constante para círculo perfecto visto desde arriba
            double x = cx + Math.cos(currentAngle) * radius;
            double z = cz + Math.sin(currentAngle) * radius;

            // Altura Y: Combinación de la Onda (Saddle) + Separación de Capa
            // La altura sigue la forma de la montura en el ángulo actual
            double saddleHeight = Math.sin(currentAngle * 2) * waveAmplitude;
            double y = cy + yLayerOffset + saddleHeight;

            // Partícula
            Location particleLoc = new Location(world.getWorld(), x, y, z);
            
            // Spawnear exactamente 1 partícula sin velocidad extra para máxima nitidez
            world.getWorld().spawnParticle(
                particleType,
                particleLoc,
                1, 
                0.0, 0.0, 0.0, 
                0.0
            );
        }
    }

    @Override
    public boolean belongsTo(Player player) {
        return this.player.equals(player);
    }
}
