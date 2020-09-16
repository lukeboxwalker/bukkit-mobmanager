package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

public class EntityManager {

    private final Map<Entity, ProtoEntity> registeredEntities = new HashMap<>();
    private final Map<Entity, ProtoEntity> activeEntities = new HashMap<>();
    private Map<Entity, ProtoEntity> tempoActiveEntities = new HashMap<>();

    public void destroyAll() {
        registeredEntities.keySet().forEach(Entity::remove);
        activeEntities.keySet().forEach(Entity::remove);
    }

    public Optional<ProtoEntity> getCustomEntity(Entity entity) {
        ProtoEntity protoEntity = activeEntities.getOrDefault(entity, tempoActiveEntities.get(entity));
        if (protoEntity != null) {
            return Optional.of(protoEntity);
        } else {
            return Optional.empty();
        }
    }

    public void tickEntities() {
        activeEntities.forEach((entity, customEntity) -> customEntity.onEntityTick(entity));
        activeEntities.putAll(tempoActiveEntities);
        tempoActiveEntities = new HashMap<>();
    }

    public boolean activateEntity(Entity entity) {
        ProtoEntity protoEntity = registeredEntities.remove(entity);
        if (protoEntity != null) {
            tempoActiveEntities.put(entity, protoEntity);
            return true;
        } else {
            return false;
        }
    }

    public void addEntity(LivingEntity entity, ProtoEntity protoEntity) {
        registeredEntities.put(entity, protoEntity);
    }

    public void removeEntity(LivingEntity entity) {
        registeredEntities.remove(entity);
        activeEntities.remove(entity);
        tempoActiveEntities.remove(entity);

    }
}
