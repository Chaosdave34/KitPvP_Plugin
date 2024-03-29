package io.github.chaosdave34.kitpvp.items.impl.enderman;

import io.github.chaosdave34.kitpvp.abilities.Ability;
import io.github.chaosdave34.kitpvp.abilities.AbilityHandler;
import io.github.chaosdave34.kitpvp.items.CustomItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnderSword extends CustomItem {
    public EnderSword() {
        super(Material.STONE_SWORD, "ender_sword");
    }

    @Override
    public @NotNull Component getName() {
        return createSimpleItemName("Ender Sword");
    }

    @Override
    public @NotNull List<Ability> getAbilities() {
        return List.of(AbilityHandler.ENDER_ATTACK);
    }

    @Override
    protected void additionalModifications(ItemStack itemStack) {
        itemStack.addEnchantment(Enchantment.DAMAGE_ALL, 3);
        setCustomModelData(itemStack, 1);
    }
}
