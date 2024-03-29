package io.github.chaosdave34.kitpvp.abilities.impl.assassin;

import io.github.chaosdave34.kitpvp.ExtendedPlayer;
import io.github.chaosdave34.kitpvp.KitPvp;
import io.github.chaosdave34.kitpvp.abilities.Ability;
import io.github.chaosdave34.kitpvp.abilities.AbilityType;
import io.github.chaosdave34.kitpvp.events.PlayerSpawnEvent;
import io.github.chaosdave34.kitpvp.kits.Kit;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HauntAbility extends Ability {
    public HauntAbility() {
        super("haunt", "Haunt", AbilityType.RIGHT_CLICK, 60);
    }

    @Override
    public @NotNull List<Component> getDescription() {
        return createSimpleDescription(
                "Gives you a speed boost and makes",
                "you invisible for 5 seconds.",
                "Runs out when you hit a player."
        );
    }

    @Override
    public boolean onAbility(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 10, 9));
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 5 * 10, 0));
        p.addScoreboardTag("haunt_ability");

        p.getInventory().setHelmet(new ItemStack(Material.AIR));
        p.getInventory().setChestplate(new ItemStack(Material.AIR));
        p.getInventory().setLeggings(new ItemStack(Material.AIR));
        p.getInventory().setBoots(new ItemStack(Material.AIR));

        Bukkit.getScheduler().runTaskLater(KitPvp.INSTANCE, () -> removeEffects(p), 5 * 20);
        return true;
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player damager) {
            removeEffects(damager);
        }
    }

    @EventHandler
    public void onSpawn(PlayerSpawnEvent e) {
        removeEffects(e.getPlayer());
    }

    private void removeEffects(Player p) {
        if (p.getScoreboardTags().contains("haunt_ability")) {
            p.removePotionEffect(PotionEffectType.SPEED);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            p.removeScoreboardTag("haunt_ability");

            Kit kit = ExtendedPlayer.from(p).getSelectedKit();
            p.getInventory().setHelmet(kit.getHeadContent());
            p.getInventory().setChestplate(kit.getChestContent());
            p.getInventory().setLeggings(kit.getLegsContent());
            p.getInventory().setBoots(kit.getFeetContent());
        }
    }
}
