package com.spectrasonic.CosmeticParticles.commands;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.commands.subcommands.HelixSubCommand;
import com.spectrasonic.CosmeticParticles.commands.subcommands.TriadSubCommand;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;
import com.spectrasonic.Utils.MessageUtils;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class CosmeticCommand {

    private final Main plugin;
    private final ParticleManager particleManager;
    private final HelixSubCommand helixSubCommand;
    private final TriadSubCommand triadSubCommand;

    public CosmeticCommand(Main plugin, ParticleManager particleManager) {
        this.plugin = plugin;
        this.particleManager = particleManager;
        this.helixSubCommand = new HelixSubCommand(plugin, particleManager);
        this.triadSubCommand = new TriadSubCommand(plugin, particleManager);
        registerCommand();
    }

    private void registerCommand() {
        new CommandAPICommand("cosmetic")
                .withPermission("cosmeticparticles.use")
                .withShortDescription("Manage particle cosmetics")
                .withFullDescription("Enable or disable particle cosmetic effects")
                .withSubcommand(createEnableCommand())
                .withSubcommand(createDisableCommand())
                .withSubcommand(helixSubCommand.getCommand())
                .withSubcommand(triadSubCommand.getCommand())
                .executes((sender, args) -> {
                    if (sender instanceof Player) {
                        showHelp((Player) sender, args);
                    }
                })
                .register(plugin);
    }

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

    private void showHelp(Player player, Object... args) {
        MessageUtils.sendMessage(player, "<gold>===== Cosmetic Particles Help =====</gold>");
        MessageUtils.sendMessage(player, "<white>Available commands:</white>");
        MessageUtils.sendMessage(player,
                "<yellow>• /cosmetic enable</yellow> - <gray>Enable default helix particle effects</gray>");
        MessageUtils.sendMessage(player,
                "<yellow>• /cosmetic disable</yellow> - <gray>Disable your particle effects</gray>");
        MessageUtils.sendMessage(player,
                "<yellow>• /cosmetic helix</yellow> - <gray>Enable magical helix particle effects</gray>");
        MessageUtils.sendMessage(player,
                "<yellow>• /cosmetic triada</yellow> - <gray>Enable mystical triad particle effects</gray>");
        MessageUtils.sendMessage(player, "");

        if (particleManager.hasActiveCosmetic(player)) {
            MessageUtils.sendMessage(player,
                    "<green>✨ Status: Particle cosmetics are currently <bold>ENABLED</bold></green>");
        } else {
            MessageUtils.sendMessage(player,
                    "<gray>💫 Status: Particle cosmetics are currently <bold>DISABLED</bold></gray>");
        }
    }
}