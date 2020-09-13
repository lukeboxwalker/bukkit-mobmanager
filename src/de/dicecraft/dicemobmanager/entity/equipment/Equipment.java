package de.dicecraft.dicemobmanager.entity.equipment;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface Equipment {

    void setItemInMainHand(ItemStack itemStack);

    void setItemInOffHand(ItemStack itemStack);

    void setHelmet(ItemStack itemStack);

    void setChestPlate(ItemStack itemStack);

    void setLeggings(ItemStack itemStack);

    void setBoots(ItemStack itemStack);

    void equip(LivingEntity entity);
}
