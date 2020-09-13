package de.dicecraft.dicemobmanager.entity.drops;

import org.bukkit.inventory.ItemStack;

public interface DeathDrop {

    ItemStack getItemStack();

    double getDropChance();

    Rarity getRarity();

    boolean shouldDrop(int lootBonus);

    enum Rarity {
        SPECIAL, MYTHIC, LEGENDARY, EPIC, RARE, UNCOMMON, COMMON
    }
}
