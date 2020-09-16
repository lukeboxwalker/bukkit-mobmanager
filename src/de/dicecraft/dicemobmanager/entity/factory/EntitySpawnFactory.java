package de.dicecraft.dicemobmanager.entity.factory;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;

import java.util.function.Consumer;

public interface EntitySpawnFactory {

    LivingEntity spawnEntity(ProtoEntity protoEntity, Location spawnLocation);

    LivingEntity spawnEntity(ProtoEntity protoEntity, Location spawnLocation, Consumer<Entity> consumer);

    Item spawnDeathDrop(DeathDrop deathDrop, ProtoEntity protoEntity, Location location);
}
