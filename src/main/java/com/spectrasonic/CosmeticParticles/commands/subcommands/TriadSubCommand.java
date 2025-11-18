package com.spectrasonic.CosmeticParticles.commands.subcommands;

import com.spectrasonic.CosmeticParticles.Main;
import com.spectrasonic.CosmeticParticles.cosmetics.CosmeticType;
import com.spectrasonic.CosmeticParticles.managers.ParticleManager;
import dev.jorel.commandapi.CommandAPICommand;
import org.bukkit.entity.Player;

public class TriadSubCommand extends SubCommand {

    public TriadSubCommand(Main plugin, ParticleManager particleManager) {
        super(plugin, particleManager);
    }

    @Override
    public CommandAPICommand getCommand() {
        return new CommandAPICommand("triada")
            .withPermission("cosmeticparticles.use")
            .withShortDescription("Enable triad particle cosmetic")
            .withFullDescription("Enables mystical triad particle effects that ascend around you")
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

        // Enable the triad cosmetic
        boolean success = particleManager.enableCosmetic(player, CosmeticType.TRIAD);

        if (success) {
            sendSuccess(player, "🔮 Triad particle cosmetics enabled! Mystical particles are now ascending around you.");
        } else {
            sendError(player, "Failed to enable triad particle cosmetics. Please try again.");
        }
    }
}