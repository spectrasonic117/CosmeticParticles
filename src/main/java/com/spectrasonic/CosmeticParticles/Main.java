package com.spectrasonic.CosmeticParticles;

import com.spectrasonic.CosmeticParticles.managers.CommandManager;
import com.spectrasonic.CosmeticParticles.managers.ConfigManager;
import com.spectrasonic.CosmeticParticles.managers.EventManager;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;

import com.spectrasonic.Utils.CommandUtils;
import com.spectrasonic.Utils.MessageUtils;

import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigManager configManager;
    private CommandManager commandManager;
    private EventManager eventManager;
    private ParticleManager particleManager;
    private JavaPlugin plugin;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        CommandAPI.onEnable();

        this.configManager = new ConfigManager(this);
        this.particleManager = new ParticleManager(this);
        this.commandManager = new CommandManager(this);
        this.eventManager = new EventManager(this);
        
        CommandUtils.setPlugin(this);
        MessageUtils.sendStartupMessage(this);
    }

    @Override
    public void onDisable() {
        // Disable all particle cosmetics before shutting down
        if (particleManager != null) {
            particleManager.disableAllCosmetics();
        }
        
        CommandAPI.onDisable();
        MessageUtils.sendShutdownMessage(this);
    }

}