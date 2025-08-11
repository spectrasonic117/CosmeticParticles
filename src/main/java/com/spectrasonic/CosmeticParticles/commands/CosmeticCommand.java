package com.spectrasonic.CosmeticParticles.commands;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;
import com.spectrasonic.Utils.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.LiteralArgument;
import org.bukkit.entity.Player;

/**
 * Handles the /cosmetic command and its subcommands
 * Provides enable and disable functionality for particle cosmetics
 */
public class CosmeticCommand {
    
    private final Main plugin;
    private final ParticleManager particleManager;
    
    /**
     * Creates a new CosmeticCommand handler
     * 
     * @param plugin The main plugin instance
     * @param particleManager The particle manager instance
     */
    public CosmeticCommand(Main plugin, ParticleManager particleManager) {
        this.plugin = plugin;
        this.particleManager = particleManager;
        registerCommand();
    }
    
    /**
     * Registers the /cosmetic command with all subcommands
     */
    private void registerCommand() {
        new CommandAPICommand("cosmetic")
            .withPermission("cosmeticparticles.use")
            .withShortDescription("Manage particle cosmetics")
            .withFullDescription("Enable or disable particle cosmetic effects")
            .withSubcommand(createEnableCommand())
            .withSubcommand(createDisableCommand())
            .executes((sender, args) -> {
                if (sender instanceof Player) {
                    showHelp((Player) sender, args);
                }
            })
            .register(plugin);
    }
    
    /**
     * Creates the enable subcommand
     * 
     * @return The enable subcommand
     */
    private CommandAPICommand createEnableCommand() {
        return new CommandAPICommand("enable")
            .withPermission("cosmeticparticles.enable")
            .withShortDescription("Enable particle cosmetics")
            .withFullDescription("Enables magical particle effects that orbit around you")
            .executes((sender, args) -> {
                if (sender instanceof Player) {
                    enableCosmetic((Player) sender, args);
                }
            });
    }
    
    /**
     * Creates the disable subcommand
     * 
     * @return The disable subcommand
     */
    private CommandAPICommand createDisableCommand() {
        return new CommandAPICommand("disable")
            .withPermission("cosmeticparticles.disable")
            .withShortDescription("Disable particle cosmetics")
            .withFullDescription("Disables your active particle cosmetic effects")
            .executes((sender, args) -> {
                if (sender instanceof Player) {
                    disableCosmetic((Player) sender, args);
                }
            });
    }
    
    /**
     * Handles the enable cosmetic command
     * 
     * @param player The player executing the command
     * @param args Command arguments (unused)
     */
    private void enableCosmetic(Player player, Object... args) {
        // Check if player already has a cosmetic
        if (particleManager.hasActiveCosmetic(player)) {
            MessageUtils.sendMessage(player, 
                "<yellow>You already have particle cosmetics enabled! Use <gold>/cosmetic disable</gold> first.</yellow>");
            return;
        }
        
        // Enable the cosmetic
        boolean success = particleManager.enableCosmetic(player);
        
        if (success) {
            MessageUtils.sendMessage(player, 
                "<green>✨ Particle cosmetics enabled! Magical particles are now orbiting around you.</green>");
        } else {
            MessageUtils.sendMessage(player, 
                "<red>❌ Failed to enable particle cosmetics. Please try again.</red>");
        }
    }
    
    /**
     * Handles the disable cosmetic command
     * 
     * @param player The player executing the command
     * @param args Command arguments (unused)
     */
    private void disableCosmetic(Player player, Object... args) {
        boolean success = particleManager.disableCosmetic(player);
        
        if (success) {
            MessageUtils.sendMessage(player, 
                "<yellow>🌟 Particle cosmetics disabled. Your magical aura has faded.</yellow>");
        } else {
            MessageUtils.sendMessage(player, 
                "<red>❌ You don't have any active particle cosmetics to disable.</red>");
        }
    }
    
    /**
     * Shows help information when the base command is used
     * 
     * @param player The player executing the command
     * @param args Command arguments (unused)
     */
    private void showHelp(Player player, Object... args) {
        MessageUtils.sendMessage(player, "<gold>===== Cosmetic Particles Help =====</gold>");
        MessageUtils.sendMessage(player, "<white>Available commands:</white>");
        MessageUtils.sendMessage(player, "<yellow>• /cosmetic enable</yellow> - <gray>Enable magical particle effects</gray>");
        MessageUtils.sendMessage(player, "<yellow>• /cosmetic disable</yellow> - <gray>Disable your particle effects</gray>");
        MessageUtils.sendMessage(player, "");
        
        if (particleManager.hasActiveCosmetic(player)) {
            MessageUtils.sendMessage(player, "<green>✨ Status: Particle cosmetics are currently <bold>ENABLED</bold></green>");
        } else {
            MessageUtils.sendMessage(player, "<gray>💫 Status: Particle cosmetics are currently <bold>DISABLED</bold></gray>");
        }
    }
}