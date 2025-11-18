package com.spectrasonic.CosmeticParticles.commands.subcommands;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;
import com.spectrasonic.Utils.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    protected final Main plugin;
    protected final ParticleManager particleManager;

    public SubCommand(Main plugin, ParticleManager particleManager) {
        this.plugin = plugin;
        this.particleManager = particleManager;
    }

    public abstract CommandAPICommand getCommand();

    protected void sendMessage(Player player, String message) {
        MessageUtils.sendMessage(player, message);
    }

    protected void sendError(Player player, String message) {
        MessageUtils.sendMessage(player, "<red>" + message + "</red>");
    }

    protected void sendSuccess(Player player, String message) {
        MessageUtils.sendMessage(player, "<green>" + message + "</green>");
    }
}