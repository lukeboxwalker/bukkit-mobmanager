package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;

import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import de.dicecraft.dicemobmanager.utils.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityManager {

    private final Map<LivingEntity, Component<ProtoEntity, Configuration>> registeredEntities = new HashMap<>();
    private final Map<LivingEntity, Component<ProtoEntity, Configuration>> activeEntities = new HashMap<>();
    private final Map<LivingEntity, Component<TickEvent, Configuration>> tickedEntities = new HashMap<>();
    private final Map<Projectile, LivingEntity> projectileMap = new HashMap<>();

    private final List<LivingEntity> deathEntities = new ArrayList<>();

    public void destroyAll() {
        registeredEntities.keySet().forEach(LivingEntity::remove);
        tickedEntities.keySet().forEach(LivingEntity::remove);
    }

    public Optional<ProtoEntity> getProtoEntity(LivingEntity entity) {
        if (tickedEntities.containsKey(entity)) {
            Component<TickEvent, Configuration> component = tickedEntities.get(entity);
            return Optional.of(component.getFirst().getProtoEntity());
        } else if (activeEntities.containsKey(entity)) {
            return Optional.of(activeEntities.get(entity).getFirst());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Configuration> getEntityConfig(LivingEntity entity) {
        if (tickedEntities.containsKey(entity)) {
            Component<TickEvent, Configuration> component = tickedEntities.get(entity);
            return Optional.of(component.getSecond());
        } else if (activeEntities.containsKey(entity)) {
            return Optional.of(activeEntities.get(entity).getSecond());
        } else {
            return Optional.empty();
        }
    }

    public void tickEntities() {
        tickedEntities.values().forEach(component -> {
            TickEvent tickEvent = component.getFirst();
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
        tickedEntities.putAll(activeEntities.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        new Component<>(new TickEvent(entry.getKey(), entry.getValue().getFirst()), entry.getValue().getSecond()))));
        activeEntities.clear();
    }


    public boolean activateEntity(LivingEntity entity) {
        Component<ProtoEntity, Configuration> component = registeredEntities.remove(entity);
        if (component != null) {
            activeEntities.put(entity, component);
            return true;
        } else {
            return false;
        }

    }

    public void registerEntity(LivingEntity entity, ProtoEntity protoEntity, Configuration configuration) {
        registeredEntities.put(entity, new Component<>(protoEntity, configuration));
    }

    public void removeEntity(LivingEntity entity) {
        registeredEntities.remove(entity);
        tickedEntities.remove(entity);
        activeEntities.remove(entity);
    }

    public void watchProjectile(Projectile projectile, LivingEntity livingEntity) {
        projectileMap.put(projectile, livingEntity);
    }

    public boolean isWatchingProjectile(Projectile projectile) {
        return projectileMap.containsKey(projectile);
    }

    public void unWatchProjectile(Projectile projectile) {
        projectileMap.remove(projectile);
    }

    public LivingEntity getProjectileShooter(Projectile projectile) {
        return projectileMap.get(projectile);
    }
}
