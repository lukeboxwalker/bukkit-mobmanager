package de.dicecraft.dicemobmanager.entity.factory;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public interface EntitySpawnFactory {

    <T extends Mob> LivingEntity spawnEntity(ProtoEntity<T> protoEntity, Location spawnLocation);
}
