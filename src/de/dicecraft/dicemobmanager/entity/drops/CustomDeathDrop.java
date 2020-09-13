package de.dicecraft.dicemobmanager.entity.drops;

import de.dicecraft.dicemobmanager.entity.CustomEntities;
import org.bukkit.inventory.ItemStack;

public class CustomDeathDrop implements DeathDrop {

    private final ItemStack itemStack;
    private final double dropChance;
    private final DeathDrop.Rarity rarity;

    public CustomDeathDrop(final ItemStack itemStack, final double dropChance, final DeathDrop.Rarity rarity) {
        this.dropChance = (dropChance > 1) ? 1.0 : ((dropChance < 0) ? 0 : dropChance);
        this.itemStack = itemStack.clone();
        this.rarity = rarity;
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public double getDropChance() {
        return dropChance;
    }

    @Override
    public DeathDrop.Rarity getRarity() {
        return rarity;
    }

    @Override
    public boolean shouldDrop(int lootBonus) {
        int dropChance = (int) (getDropChance() * 10000 + lootBonus * 1000 * getDropChance());
        return (CustomEntities.randomIntBetween(0, 10000) < dropChance);
    }
}

