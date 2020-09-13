package de.dicecraft.dicemobmanager.entity.drops;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.EntityManager;
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
public class CustomDeathDrop implements DeathDrop {

    private final double dropChance;
    private final DeathDrop.Rarity rarity;
    private final ItemStack itemStack;

    /**
     * Creates new CustomDeathDrop.
     * <p>
     * Checks whether the drop chance is between 0 and 1.
     * If the drop chance is below 0 the chance is set to 0.
     * If the drop chance is above 1 the chance is set to 1.
     *
     * @param itemStack the itemStack to drop
     * @param dropChance the drop chance of the item
     * @param rarity the rarity of the drop
     */
    public CustomDeathDrop(ItemStack itemStack, double dropChance, DeathDrop.Rarity rarity) {
        this.dropChance = (dropChance > 1) ? 1.0 : ((dropChance < 0) ? 0 : dropChance);
        this.itemStack = itemStack.clone();
        this.rarity = rarity;
    }

    /**
     * Provides the itemStack that should drop.
     *
     * @return the itemStack
     */
    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Gets the drop chance of the item.
     *
     * @return the drop chance
     */
    @Override
    public double getDropChance() {
        return dropChance;
    }

    /**
     * Gets the rarity of the item.
     *
     * @return the rarity
     */
    @Override
    public DeathDrop.Rarity getRarity() {
        return rarity;
    }

    /**
     * Calculates if the item should drop.
     * <p>
     * Using the lootBonus (e.g looting enchantment) to
     * decide whether the item should drop
     *
     * @return if the item should drop
     */
    @Override
    public boolean shouldDrop(int lootBonus) {
        int dropChance = (int) (getDropChance() * 10000 + lootBonus * 1000 * getDropChance());
        return (DiceMobManager.randomIntBetween(0, 10000) < dropChance);
    }

    @Override
    public String toString() {
        return "CustomDeathDrop{" +
                "dropChance=" + dropChance +
                ", rarity=" + rarity +
                ", itemStack=" + itemStack +
                '}';
    }
}

