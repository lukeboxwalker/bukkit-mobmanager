package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;

import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityManager {

    private final Map<LivingEntity, ProtoEntity> registeredEntities = new HashMap<>();
    private final Map<LivingEntity, ProtoEntity> activeEntities = new HashMap<>();
    private final Map<LivingEntity, TickEvent> tickedEntities = new HashMap<>();

    public void destroyAll() {
        registeredEntities.keySet().forEach(LivingEntity::remove);
        tickedEntities.keySet().forEach(LivingEntity::remove);
    }

    public Optional<ProtoEntity> getProtoEntity(LivingEntity entity) {
        if (tickedEntities.containsKey(entity)) {
            TickEvent tickEvent = tickedEntities.get(entity);
            return Optional.of(tickEvent.getProtoEntity());
        } else if (activeEntities.containsKey(entity)) {
            return Optional.of(activeEntities.get(entity));
        } else {
            return Optional.empty();
        }
    }

    public void tickEntities() {
        tickedEntities.values().forEach(tickEvent -> tickEvent.getProtoEntity().onEntityTick(tickEvent));
        tickedEntities.putAll(activeEntities.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new TickEvent(entry.getKey(), entry.getValue()))));
        activeEntities.clear();
    }

    public boolean activateEntity(LivingEntity entity) {
        ProtoEntity protoEntity = registeredEntities.remove(entity);
        if (protoEntity != null) {
            activeEntities.put(entity, protoEntity);
            return true;
        } else {
            return false;
        }
    }

    public void registerEntity(LivingEntity entity, ProtoEntity protoEntity) {
        registeredEntities.put(entity, protoEntity);
    }

    public void removeEntity(LivingEntity entity) {
        registeredEntities.remove(entity);
        tickedEntities.remove(entity);
        activeEntities.remove(entity);

    }
}
