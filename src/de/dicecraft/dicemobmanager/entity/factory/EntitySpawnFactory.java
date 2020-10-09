package de.dicecraft.dicemobmanager.entity.factory;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import java.util.function.Consumer;

public interface EntitySpawnFactory {

    <T extends Mob> LivingEntity spawnEntity(ProtoEntity<T> protoEntity, Location spawnLocation);

    <T extends Mob> LivingEntity spawnEntity(ProtoEntity<T> protoEntity, Location spawnLocation, Consumer<Entity> consumer);
}
