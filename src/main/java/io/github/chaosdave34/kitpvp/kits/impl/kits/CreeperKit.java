package io.github.chaosdave34.kitpvp.kits.impl.kits;

import io.github.chaosdave34.kitpvp.items.CustomItemHandler;
import io.github.chaosdave34.kitpvp.kits.Kit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class CreeperKit extends Kit {
    public CreeperKit() {
        super("creeper", "Creeper");
    }

    @Override
    public ItemStack getHeadContent() {
        ItemStack creeperHead = new ItemStack(Material.CREEPER_HEAD);
        creeperHead.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        creeperHead.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
        return creeperHead;
    }

    @Override
    public ItemStack getChestContent() {
        ItemStack leatherChestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        leatherChestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leatherChestplate.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
        setLeatherArmorColor(leatherChestplate, Color.LIME);
        return leatherChestplate;
    }

    @Override
    public ItemStack getLegsContent() {
        return CustomItemHandler.CREEPER_LEGGINGS.build();
    }

    @Override
    public ItemStack getFeetContent() {
        ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS);
        leatherBoots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leatherBoots.addUnsafeEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 4);
        setLeatherArmorColor(leatherBoots, Color.LIME);
        return leatherBoots;
    }

    @Override
    public ItemStack[] getInventoryContent() {
        return new ItemStack[]{
                CustomItemHandler.FIREBALL_SWORD.build(),
                new ItemStack(Material.WATER_BUCKET),
        };
    }

    @Override
    public ItemStack[] getKillRewards() {
        return new ItemStack[]{
                new ItemStack(Material.GOLDEN_APPLE),
                new ItemStack(Material.COBBLESTONE, 32),
        };
    }
}
