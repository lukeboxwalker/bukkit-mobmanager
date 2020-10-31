package de.dicecraft.dicemobmanager.entity.equipment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * Equipment for an entity.
 * <p>
 * Specifies all equipment slots of an entity.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class CustomEquipment implements Equipment {

    // equipment
    private ItemStack mainHand;
    private ItemStack offHand;
    private ItemStack helmet;
    private ItemStack chestPlate;
    private ItemStack leggings;
    private ItemStack boots;

    /**
     * Sets the main hand equipment.
     *
     * @param itemStack to equip
     */
    @Override
    public void setItemInMainHand(final ItemStack itemStack) {
        this.mainHand = itemStack;
    }

    /**
     * Sets the off hand equipment.
     *
     * @param itemStack to equip
     */
    @Override
    public void setItemInOffHand(final ItemStack itemStack) {
        this.offHand = itemStack;
    }

    /**
     * Sets the helmet (armor) equipment.
     *
     * @param itemStack to equip
     */
    @Override
    public void setHelmet(final ItemStack itemStack) {
        this.helmet = itemStack;
    }

    /**
     * Sets the chest plate (armor) equipment.
     *
     * @param itemStack to equip
     */
    @Override
    public void setChestPlate(final ItemStack itemStack) {
        this.chestPlate = itemStack;
    }

    /**
     * Sets the leggings (armor) equipment.
     *
     * @param itemStack to equip
     */
    @Override
    public void setLeggings(final ItemStack itemStack) {
        this.leggings = itemStack;
    }

    /**
     * Sets the boots (armor) equipment.
     *
     * @param itemStack to equip
     */
    @Override
    public void setBoots(final ItemStack itemStack) {
        this.boots = itemStack;
    }

    /**
     * Equips the given entity with the configured equipment.
     * <p>
     * Only a living entity has the opportunity to equip
     * gear etc. Copies all equipment itemStacks to ensure
     * reusability of this equipment configuration.
     * <p>
     * If no equipment is set for a specific slot, the slot is
     * equipped with nothing.
     *
     * @param entity to equip
     */
    @Override
    public void equip(final LivingEntity entity) {
        final EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            // clear the previous equipment
            equipment.clear();

            // set new gear by cloning the itemStack
            equipment.setItemInMainHand(Objects.nonNull(mainHand) ? mainHand.clone() : null);
            equipment.setItemInMainHandDropChance(0);
            equipment.setItemInOffHand(Objects.nonNull(offHand) ? offHand.clone() : null);
            equipment.setItemInOffHandDropChance(0);
            equipment.setHelmet(Objects.nonNull(helmet) ? helmet.clone() : null);
            equipment.setHelmetDropChance(0);
            equipment.setChestplate(Objects.nonNull(chestPlate) ? chestPlate.clone() : null);
            equipment.setChestplateDropChance(0);
            equipment.setLeggings(Objects.nonNull(leggings) ? leggings.clone() : null);
            equipment.setLeggingsDropChance(0);
            equipment.setBoots(Objects.nonNull(boots) ? boots.clone() : null);
            equipment.setBootsDropChance(0);
        }
    }

    @Override
    public String toString() {
        return "CustomEquipment{" +
                "mainHand=" + mainHand +
                ", offHand=" + offHand +
                ", helmet=" + helmet +
                ", chestPlate=" + chestPlate +
                ", leggings=" + leggings +
                ", boots=" + boots +
                '}';
    }
}
