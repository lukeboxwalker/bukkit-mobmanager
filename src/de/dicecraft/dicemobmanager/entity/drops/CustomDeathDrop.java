package de.dicecraft.dicemobmanager.entity.drops;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

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

    private static final int LOOT_MULTIPLIER = 10_000;
    private static final int DROP_MULTIPLIER = 10_0000;

    private double dropChance;
    private DeathDrop.Rarity rarity;
    private ItemStack itemStack;

    /**
     * Creates new CustomDeathDrop.
     * <p>
     * Checks whether the drop chance is between 0 and 1.
     * If the drop chance is below 0 the chance is set to 0.
     * If the drop chance is above 1 the chance is set to 1.
     *
     * @param itemStack  the itemStack to drop
     * @param dropChance the drop chance of the item
     * @param rarity     the rarity of the drop
     */
    public CustomDeathDrop(ItemStack itemStack, double dropChance, DeathDrop.Rarity rarity) {
        this.setDropChance(dropChance);
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

    @Override
    public void setItemStack(@Nonnull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void setDropChance(double dropChance) {
        this.dropChance = (dropChance > 1) ? 1.0 : ((dropChance < 0) ? 0 : dropChance);
    }

    @Override
    public void setRarity(@Nonnull Rarity rarity) {
        this.rarity = rarity;
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
        int dropChance = (int) (getDropChance() * DROP_MULTIPLIER + lootBonus * LOOT_MULTIPLIER * getDropChance());
        return (DiceMobManager.randomIntBetween(0, DROP_MULTIPLIER) < dropChance);
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

