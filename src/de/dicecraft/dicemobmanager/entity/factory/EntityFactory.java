package de.dicecraft.dicemobmanager.entity.factory;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.function.Consumer;

public interface EntityFactory {

    LivingEntity spawnEntity(ProtoEntity protoEntity, Location spawnLocation);

    LivingEntity spawnEntity(ProtoEntity protoEntity, Location spawnLocation, Consumer<Entity> consumer);
}
