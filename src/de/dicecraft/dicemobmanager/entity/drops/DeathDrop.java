package de.dicecraft.dicemobmanager.entity.drops;

import org.bukkit.inventory.ItemStack;

/**
 * Loot an entity can drop.
 * <p>
 * Specifies the rarity of the loot
 * as well as the drop chance.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface DeathDrop {

    /**
     * Provides the itemStack that should drop.
     *
     * @return the itemStack
     */
    ItemStack getItemStack();

    /**
     * Gets the drop chance of the item.
     *
     * @return the drop chance
     */
    double getDropChance();

    /**
     * Gets the rarity of the item.
     *
     * @return the rarity
     */
    Rarity getRarity();

    /**
     * Calculates if the item should drop.
     * <p>
     * Using the lootBonus (e.g looting enchantment) to
     * decide whether the item should drop
     *
     * @return if the item should drop
     */
    boolean shouldDrop(int lootBonus);

    /**
     * Rarity of a drop.
     * <p>
     * Death drops have a rarity based on
     * there rareness.
     *
     * @author Walkehorst Lukas
     * @since 1.0
     */
    enum Rarity {
        SPECIAL, MYTHIC, LEGENDARY, EPIC, RARE, UNCOMMON, COMMON
    }
}
