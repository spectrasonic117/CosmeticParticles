package com.spectrasonic.CosmeticParticles.commands.subcommands;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.cosmetics.CosmeticType;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class HelixSubCommand extends SubCommand {

    public HelixSubCommand(Main plugin, ParticleManager particleManager) {
        super(plugin, particleManager);
    }

    @Override
    public CommandAPICommand getCommand() {
        return new CommandAPICommand("helix")
            .withPermission("cosmeticparticles.use")
            .withShortDescription("Enable helix particle cosmetic")
            .withFullDescription("Enables magical helix particle effects that orbit around you")
            .executes((sender, args) -> {
                if (sender instanceof Player) {
                    execute((Player) sender);
                }
            });
    }

    private void execute(Player player) {
        // Check if player already has a cosmetic
        if (particleManager.hasActiveCosmetic(player)) {
            sendError(player, "You already have particle cosmetics enabled! Use /cosmetic disable first.");
            return;
        }

        // Enable the helix cosmetic
        boolean success = particleManager.enableCosmetic(player, CosmeticType.HELIX);

        if (success) {
            sendSuccess(player, "✨ Helix particle cosmetics enabled! Magical particles are now orbiting around you.");
        } else {
            sendError(player, "Failed to enable helix particle cosmetics. Please try again.");
        }
    }
}