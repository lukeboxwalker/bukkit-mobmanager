package de.dicecraft.dicemobmanager.entity.enchatment;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface EnchantmentHandler {

    void handle(LivingEntity attacked, ProtoEntity<?> attackedProtoEntity, Player attacker);

}
