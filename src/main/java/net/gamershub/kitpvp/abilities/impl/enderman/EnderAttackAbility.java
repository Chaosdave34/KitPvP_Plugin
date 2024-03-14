package net.gamershub.kitpvp.abilities.impl.enderman;

import net.gamershub.kitpvp.ExtendedPlayer;
import net.gamershub.kitpvp.KitPvpPlugin;
import net.gamershub.kitpvp.abilities.Ability;
import net.gamershub.kitpvp.abilities.AbilityType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnderAttackAbility extends Ability {
    public EnderAttackAbility() {
        super("ender_attack", "Ender Attack", AbilityType.RIGHT_CLICK, 15);
    }

    @Override
    public @NotNull List<Component> getDescription() {
        return createSimpleDescription(
                "Teleports you behind the player",
                "you are looking at in a 50",
                "block radius. Gain speed and ",
                "strength for 5 seconds."
        );
    }

    @Override
    public boolean onAbility(Player p) {
        Entity target = p.getTargetEntity(50, true);
        if (target instanceof LivingEntity livingEntity) {

            if (target instanceof Player player) {
                if (KitPvpPlugin.INSTANCE.getExtendedPlayer(player).getGameState() == ExtendedPlayer.GameState.SPAWN)
                    return false;
            }

            p.teleport(livingEntity.getLocation().subtract(target.getLocation().getDirection()));

            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 1));
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5 * 20, 1));

            return true;
        }
        return false;
    }
}
