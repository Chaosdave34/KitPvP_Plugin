package io.github.chaosdave34.kitpvp.items;

import io.github.chaosdave34.ghutils.utils.PDCUtils;
import io.github.chaosdave34.kitpvp.KitPvp;
import io.github.chaosdave34.kitpvp.abilities.Ability;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class CustomItem implements Listener {
    @Getter
    protected String id;
    protected Material material;
    protected boolean stackable;
    protected boolean preventPlacingAndUsing;

    public CustomItem(Material material, String id) {
        this.material = material;
        this.id = id;
        this.stackable = true;
        this.preventPlacingAndUsing = false;
    }

    public CustomItem(Material material, String id, boolean stackable) {
        this.material = material;
        this.id = id;
        this.stackable = stackable;
        this.preventPlacingAndUsing = false;
    }

    public CustomItem(Material material, String id, boolean stackable, boolean preventPlacingAndUsing) {
        this.material = material;
        this.id = id;
        this.stackable = stackable;
        this.preventPlacingAndUsing = preventPlacingAndUsing;
    }

    @NotNull
    public abstract Component getName();

    @NotNull
    public List<Component> getDescription() {
        return Collections.emptyList();
    }

    @NotNull
    public List<Ability> getAbilities() {
        return Collections.emptyList();
    }

    public ItemStack build() {
        return build(1);
    }

    public ItemStack build(int count) {
        ItemStack itemStack = new ItemStack(material, count);
        ItemMeta itemMeta = itemStack.getItemMeta();


        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.displayName(getName());

        PDCUtils.setId(itemMeta, id);

        if (!stackable) {
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            container.set(new NamespacedKey(KitPvp.INSTANCE, "uuid"), PersistentDataType.STRING, UUID.randomUUID().toString());
        }

        itemStack.setItemMeta(itemMeta);

        for (Ability ability : getAbilities()) {
            ability.apply(itemStack);
        }

        additionalModifications(itemStack);

        setCustomLore(itemStack);

        return itemStack;

    }

    private void setCustomLore(ItemStack itemStack) {
        // Description
        List<Component> lore = new ArrayList<>(getDescription());

        if (!getDescription().isEmpty()) lore.add(Component.empty());

        // Enchantments
        List<Component> enchantmentLore = new ArrayList<>();

        for (Map.Entry<Enchantment, Integer> enchantment : itemStack.getEnchantments().entrySet()) {
            Component component = enchantment.getKey().displayName(enchantment.getValue()).decoration(TextDecoration.ITALIC, false);
            enchantmentLore.add(component);
        }

        enchantmentLore.sort(Comparator.comparing(s -> PlainTextComponentSerializer.plainText().serialize(s).toLowerCase()));

        lore.addAll(enchantmentLore);

        if (!enchantmentLore.isEmpty()) lore.add(Component.empty());

        // Abilities
        Iterator<Ability> abilities = KitPvp.INSTANCE.getAbilityHandler().getItemAbilities(itemStack).iterator();
        while (abilities.hasNext()) {
            Ability ability = abilities.next();

            Component name = Component.text("Ability: ", NamedTextColor.GREEN)
                    .append(Component.text(ability.getName(), NamedTextColor.GOLD))
                    .append(Component.text("  "))
                    .append(Component.text(ability.getType().getDisplayName(), NamedTextColor.YELLOW, TextDecoration.BOLD))
                    .decoration(TextDecoration.ITALIC, false);

            lore.add(name);

            lore.addAll(ability.getDescription());

            Component cooldown = Component.text("Cooldown: ", NamedTextColor.DARK_GRAY)
                    .append(Component.text(ability.getCooldown(), NamedTextColor.GREEN))
                    .append(Component.text("s", NamedTextColor.GREEN))
                    .decoration(TextDecoration.ITALIC, false);

            lore.add(cooldown);

            if (abilities.hasNext()) lore.add(Component.empty());
        }

        if (!lore.isEmpty() && PlainTextComponentSerializer.plainText().serialize(lore.get(lore.size() - 1)).isEmpty()) {
            lore.remove(lore.size() - 1);
        }

        itemStack.lore(lore);
    }

    protected void additionalModifications(ItemStack itemStack) {
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (preventPlacingAndUsing && id.equals(CustomItemHandler.getCustomItemId(e.getItemInHand()))) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (e.getItem() == null) return;
        if (preventPlacingAndUsing && id.equals(CustomItemHandler.getCustomItemId(e.getItem()))) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                e.setCancelled(true);
            }
        }
    }

    protected void setLeatherArmorColor(ItemStack leatherArmor, Color color) {
        leatherArmor.editMeta(LeatherArmorMeta.class, leatherArmorMeta -> leatherArmorMeta.setColor(color));
    }

    protected void setCustomModelData(ItemStack itemStack, Integer customModelData) {
        itemStack.editMeta(itemMeta -> itemMeta.setCustomModelData(customModelData));
    }

    protected Component createSimpleItemName(String name) {
        return Component.text(name).decoration(TextDecoration.ITALIC, false);
    }

    protected List<Component> createSimpleDescription(String... lines) {
        List<Component> componentList = new ArrayList<>();
        for (String line : lines)
            componentList.add(Component.text(line, NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false));
        return componentList;
    }

}
