package de.dicecraft.dicemobmanager.entity.factory;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.function.Consumer;

public interface EntityFactory {

    Entity spawnEntity(ProtoEntity protoEntity, Location spawnLocation);

    Entity spawnEntity(ProtoEntity protoEntity, Location spawnLocation, Consumer<Entity> consumer);
}
