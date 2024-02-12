package net.gamershub.kitpvp;

import lombok.Getter;
import lombok.Setter;
import net.gamershub.kitpvp.kits.Kit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class ExtendedPlayer {
    private final UUID uuid;
    private GameState gameState;
    private String selectedKitId;

    public ExtendedPlayer(Player p) {
        uuid = p.getUniqueId();
        gameState = GameState.LOBBY;
    }

    public Kit getSelectedKit() {
        return selectedKitId == null ? null : KitPvpPlugin.INSTANCE.getKitHandler().getKits().get(selectedKitId);
    }

    public enum GameState {
        LOBBY,
        IN_GAME
    }
}
