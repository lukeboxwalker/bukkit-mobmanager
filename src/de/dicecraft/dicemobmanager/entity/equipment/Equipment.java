package de.dicecraft.dicemobmanager.entity.equipment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 * Equipment for an entity.
 * <p>
 * Specifies all equipment slots of an entity.
 * Possible equipment slots:
 * - MainHand
 * - OffHand
 * - Helmet
 * - ChestPlate
 * - Leggings
 * - Boots
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface Equipment {

    /**
     * Sets the main hand equipment.
     *
     * @param itemStack to equip
     */
    void setItemInMainHand(ItemStack itemStack);

    /**
     * Sets the off hand equipment.
     *
     * @param itemStack to equip
     */
    void setItemInOffHand(ItemStack itemStack);

    /**
     * Sets the helmet (armor) equipment.
     *
     * @param itemStack to equip
     */
    void setHelmet(ItemStack itemStack);

    /**
     * Sets the chest plate (armor) equipment.
     *
     * @param itemStack to equip
     */
    void setChestPlate(ItemStack itemStack);

    /**
     * Sets the leggings (armor) equipment.
     *
     * @param itemStack to equip
     */
    void setLeggings(ItemStack itemStack);

    /**
     * Sets the boots (armor) equipment.
     *
     * @param itemStack to equip
     */
    void setBoots(ItemStack itemStack);

    /**
     * Equips the given entity with the configured equipment.
     * <p>
     * Only a living entity has the opportunity to equip
     * gear etc.
     *
     * @param entity to equip
     */
    void equip(LivingEntity entity);
}
