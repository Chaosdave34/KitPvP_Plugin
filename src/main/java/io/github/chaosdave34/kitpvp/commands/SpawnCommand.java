package io.github.chaosdave34.kitpvp.commands;

import io.github.chaosdave34.kitpvp.ExtendedPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            ExtendedPlayer extendedPlayer = ExtendedPlayer.from(p);

            if (extendedPlayer.getCombatCooldown() == 0) {
                extendedPlayer.spawn();
            } else {
                p.sendMessage(Component.text("You are still in combat for " + extendedPlayer.getCombatCooldown() + "s!", NamedTextColor.RED));
            }
            return true;
        }
        return false;
    }
}
