package com.spectrasonic.CosmeticParticles.managers;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.commands.CosmeticCommand;
import lombok.Getter;

@Getter
public class CommandManager {

    private final Main plugin;
    private CosmeticCommand cosmeticCommand;

    public CommandManager(Main plugin) {
        this.plugin = plugin;
        registerCommands();
    }

    private void registerCommands() {
        this.cosmeticCommand = new CosmeticCommand(plugin, plugin.getParticleManager());
    }
}