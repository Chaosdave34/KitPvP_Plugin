package io.github.chaosdave34.kitpvp.textdisplays;

import io.github.chaosdave34.ghutils.textdisplay.TextDisplay;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class JumpTextDisplay extends TextDisplay {
    public JumpTextDisplay() {
        super("world", new Location(null, 2.0, 120, 2.0), 1);
    }

    @Override
    public @NotNull List<net.minecraft.network.chat.Component> getLines(Player p) {
        return List.of(Component.literal("JUMP!").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD));
    }
}
