package de.dicecraft.dicemobmanager;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LastAttacker {

    private final UUID uuid;
    private final long timeStamp;
    private final Map<Enchantment, Integer> enchantments;

    public LastAttacker(final LivingEntity attacker) {
        this.uuid = attacker.getUniqueId();
        this.timeStamp = System.currentTimeMillis();
        EntityEquipment equipment = attacker.getEquipment();
        this.enchantments = equipment == null ? new HashMap<>() : equipment.getItemInMainHand().getEnchantments();
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }
}
