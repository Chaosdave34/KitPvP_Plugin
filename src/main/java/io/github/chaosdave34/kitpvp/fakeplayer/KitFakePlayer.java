package io.github.chaosdave34.kitpvp.fakeplayer;

import io.github.chaosdave34.ghutils.fakeplayer.FakePlayer;
import io.github.chaosdave34.kitpvp.ExtendedPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KitFakePlayer extends FakePlayer {
    public KitFakePlayer() {
        super("KitPvP", "world_elytra", new Location(null, 16.5, 200, -2.5), 10, 0, true);
    }

    @Override
    public void onAttack(@NotNull Player p) {
        ExtendedPlayer.from(p).spawn(ExtendedPlayer.GameType.KITS);
    }

    @Override
    public void onActualInteract(@NotNull Player p) {
        ExtendedPlayer.from(p).spawn(ExtendedPlayer.GameType.KITS);
    }
}
