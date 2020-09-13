package de.dicecraft.dicemobmanager.entity.equipment;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CustomEquipment implements Equipment {

    private ItemStack mainHand;
    private ItemStack offHand;
    private ItemStack helmet;
    private ItemStack chestPlate;
    private ItemStack leggings;
    private ItemStack boots;

    @Override
    public void setItemInMainHand(ItemStack mainHand) {
        this.mainHand = mainHand;
    }

    @Override
    public void setItemInOffHand(ItemStack offHand) {
        this.offHand = offHand;
    }

    @Override
    public void setHelmet(ItemStack helmet) {
        this.helmet = helmet;
    }

    @Override
    public void setChestPlate(ItemStack chestPlate) {
        this.chestPlate = chestPlate;
    }

    @Override
    public void setLeggings(ItemStack leggings) {
        this.leggings = leggings;
    }

    @Override
    public void setBoots(ItemStack boots) {
        this.boots = boots;
    }

    @Override
    public void equip(LivingEntity entity) {
        EntityEquipment equipment = entity.getEquipment();
        if (equipment != null) {
            equipment.clear();
            equipment.setItemInMainHand(Objects.nonNull(mainHand) ? mainHand.clone() : null);
            equipment.setItemInOffHand(Objects.nonNull(offHand) ? offHand.clone() : null);
            equipment.setHelmet(Objects.nonNull(helmet) ? helmet.clone() : null);
            equipment.setChestplate( Objects.nonNull(chestPlate) ? chestPlate.clone() : null);
            equipment.setLeggings(Objects.nonNull(leggings) ? leggings.clone() : null);
            equipment.setBoots(Objects.nonNull(boots) ? boots.clone() : null);

        }

    }
}
