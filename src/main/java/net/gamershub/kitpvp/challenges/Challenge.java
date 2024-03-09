package net.gamershub.kitpvp.challenges;

import lombok.Getter;
import net.gamershub.kitpvp.ExtendedPlayer;
import net.gamershub.kitpvp.KitPvpPlugin;
import net.gamershub.kitpvp.customevents.CustomEventHandler;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class Challenge implements Listener {
    protected String id;
    protected String name;
    protected int amount;
    protected final Map<Player, Integer> progress = new HashMap<>();

    public Challenge(String id, String name, int amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    protected void incrementProgress(Player p) {
        ExtendedPlayer extendedPlayer = KitPvpPlugin.INSTANCE.getExtendedPlayer(p);

        if (getProgress(p) == amount - 1) {
            int xpReward = 50;
            if (KitPvpPlugin.INSTANCE.getCustomEventHandler().getActiveEvent() == CustomEventHandler.DOUBLE_EXPERIENCE_EVENT)
                xpReward *= 2;

            p.sendMessage(Component.text("You have completed a daily Challenge! +" + xpReward + "XP"));
            extendedPlayer.addExperiencePoints(xpReward);
        }

        if (getProgress(p) < amount) {
            if (progress.containsKey(p))
                progress.put(p, progress.get(p) + 1);
            else
                progress.put(p, 1);
        }


        extendedPlayer.updatePlayerListFooter();
    }

    public int getProgress(Player p) {
        if (!progress.containsKey(p))
            progress.put(p, 0);

        return progress.get(p);
    }

    public void resetProgress(Player p) {
        progress.remove(p);
    }
}
