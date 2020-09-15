package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.builder.CustomEntity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

public class EntityManager {

    private final Map<Entity, CustomEntity> registeredEntities = new HashMap<>();
    private final Map<Entity, CustomEntity> activeEntities = new HashMap<>();
    private Map<Entity, CustomEntity> tempoActiveEntities = new HashMap<>();

    public void destroyAll() {
        registeredEntities.keySet().forEach(Entity::remove);
        activeEntities.keySet().forEach(Entity::remove);
    }

    public Optional<CustomEntity> getCustomEntity(Entity entity) {
        CustomEntity customEntity = activeEntities.getOrDefault(entity, tempoActiveEntities.get(entity));
        if (customEntity != null) {
            return Optional.of(customEntity);
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
        CustomEntity customEntity = registeredEntities.remove(entity);
        if (customEntity != null) {
            tempoActiveEntities.put(entity, customEntity);
            return true;
        } else {
            return false;
        }
    }

    public void addEntity(LivingEntity entity, CustomEntity customEntity) {
        registeredEntities.put(entity, customEntity);
    }

    public void removeEntity(LivingEntity entity) {
        registeredEntities.remove(entity);
        activeEntities.remove(entity);
        tempoActiveEntities.remove(entity);

    }
}
