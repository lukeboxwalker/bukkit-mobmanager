package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;

import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityManager {

    private final Map<LivingEntity, ProtoEntity> registeredEntities = new HashMap<>();
    private final Map<LivingEntity, ProtoEntity> activeEntities = new HashMap<>();
    private final Map<LivingEntity, TickEvent> tickedEntities = new HashMap<>();

    private final List<LivingEntity> deathEntities = new ArrayList<>();

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
        tickedEntities.values().forEach(tickEvent -> {
            LivingEntity entity = tickEvent.getEntity();
            if (entity.isDead()) {
                // only remove entity on tick if entity is not a slimes with size > 1
                // because the slime will be removed when the slime split event
                // of this entity was called.
                if (!(entity instanceof Slime && ((Slime) entity).getSize() > 1)) {
                    deathEntities.add(tickEvent.getEntity());
                }
            } else {
                // ticking the entity
                tickEvent.getProtoEntity().onEntityTick(tickEvent);
            }
        });

        // removing all death entities (expect slimes)
        deathEntities.forEach(tickedEntities::remove);
        deathEntities.clear();

        // add waiting entities to ticking map so they can be
        // ticked by this method
        tickedEntities.putAll(activeEntities.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new TickEvent(entry.getKey(), entry.getValue()))));
        activeEntities.clear();
    }

    public Map<LivingEntity, TickEvent> getTickedEntities() {
        return tickedEntities;
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
