package net.gamershub.kitpvp.textdisplays.impl;

import net.gamershub.kitpvp.ExtendedPlayer;
import net.gamershub.kitpvp.KitPvpPlugin;
import net.gamershub.kitpvp.textdisplays.TextDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PersonalStatisticsDisplay extends TextDisplay {
    public PersonalStatisticsDisplay() {
        super("world", new Location(null, -3.5, 101.5, -9.5), 5);
    }

    @Override
    public @NotNull List<Component> getLines(Player p) {
        ExtendedPlayer extendedPlayer = KitPvpPlugin.INSTANCE.getExtendedPlayer(p);
        double killDeathRation = extendedPlayer.getTotalDeaths() == 0 ? extendedPlayer.getTotalKills() : (double) extendedPlayer.getTotalKills() / extendedPlayer.getTotalDeaths();
        return List.of(
                Component.literal("Personal Statistics:").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD),
                Component.literal("Highest Killstreak: " + extendedPlayer.getHighestKillStreak()),
                Component.literal("Kills: " + extendedPlayer.getTotalKills()),
                Component.literal("Deaths: " + extendedPlayer.getTotalDeaths()),
                Component.literal((("K/D: " + (((double) Math.round(killDeathRation * 100)) / 100))))
        );

    }
}