package io.github.chaosdave34.kitpvp.enchantments;

import io.github.chaosdave34.ghutils.enchantment.CustomEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BackstabEnchantment extends CustomEnchantment {
    public BackstabEnchantment() {
        super("backstab", 1, "Backstab", EnchantmentCategory.WEAPON, EquipmentSlot.MAINHAND);
    }

    @EventHandler
    public void onDealDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player damager) {
            if (damager.getInventory().getItemInMainHand().containsEnchantment(CustomEnchantments.BACKSTAB)) {
                if (Math.abs(damager.getLocation().getDirection().angle(e.getEntity().getLocation().getDirection()) * 180 / Math.PI) < 30) {
                    e.setDamage(e.getDamage() * 2);
                }
            }
        }
    }
}
