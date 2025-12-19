package com.spectrasonic.CosmeticParticles.cosmetics;

public enum CosmeticType {
    HELIX("helix", "Efecto de hélice mágico"),
    TRIAD("triada", "Efecto de triada ascendente"),
    WAVE_SADDLE("montura", "Montura ondulada de 3 capas rotatorias"),
    PULSATING_RING("anillo", "Anillo pulsante brillante"),
    DOUBLE_HELIX("doble_helix", "Efecto de resorte doble giratorio");

    private final String name;
    private final String description;

    CosmeticType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static CosmeticType fromString(String name) {
        for (CosmeticType type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}