package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;

import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import de.dicecraft.dicemobmanager.entity.goals.EntitySelector;
import de.dicecraft.dicemobmanager.utils.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityManager {

    private final Map<Entity, Component<ProtoEntity, Configuration>> registeredEntities = new HashMap<>();
    private final Map<Entity, Component<ProtoEntity, Configuration>> activeEntities = new HashMap<>();
    private final Map<Entity, Component<TickEvent, Configuration>> tickedEntities = new HashMap<>();
    private final Map<Entity, Entity> projectileMap = new HashMap<>();

    private final List<LivingEntity> deathEntities = new ArrayList<>();
    private final Function<Map.Entry<Entity, Component<ProtoEntity, Configuration>>, TickEvent> toTickEvent =
            entry -> new TickEvent((LivingEntity) entry.getKey(), entry.getValue().getFirst());

    private boolean canItemsDrop = true;

    public boolean canItemsDrop() {
        return canItemsDrop;
    }

    public void setItemsDrop(boolean canItemsDrop) {
        this.canItemsDrop = canItemsDrop;
    }

    public void destroyAll() {
        registeredEntities.keySet().forEach(Entity::remove);
        tickedEntities.keySet().forEach(Entity::remove);
    }

    public Map<Entity, ProtoEntity> getAllEntities(Entity... entities) {
        Map<Entity, ProtoEntity> result = new HashMap<>();
        if (entities.length == 0) {
            activeEntities.forEach((entity, component) -> result.put(entity, component.getFirst()));
            tickedEntities.forEach((entity, component) -> result.put(entity, component.getFirst().getProtoEntity()));
        } else {
            Arrays.stream(entities).forEach(entity -> {
                if (activeEntities.containsKey(entity)) {
                    result.put(entity, activeEntities.get(entity).getFirst());
                }
                if (tickedEntities.containsKey(entity)) {
                    result.put(entity, tickedEntities.get(entity).getFirst().getProtoEntity());
                }
            });
        }
        return result;
    }

    public Optional<ProtoEntity> getProtoEntity(Entity entity) {
        if (tickedEntities.containsKey(entity)) {
            Component<TickEvent, Configuration> component = tickedEntities.get(entity);
            return Optional.of(component.getFirst().getProtoEntity());
        } else if (activeEntities.containsKey(entity)) {
            return Optional.of(activeEntities.get(entity).getFirst());
        } else {
            return Optional.empty();
        }
    }

    public Optional<Configuration> getEntityConfig(Entity entity) {
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
                if (!(EntitySelector.IS_SLIME_ENTITY.test(entity) && ((Slime) entity).getSize() > 1)) {
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
                .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        new Component<>(toTickEvent.apply(entry), entry.getValue().getSecond()))));
        activeEntities.clear();
    }


    public Optional<ProtoEntity> canActivateEntity(Entity entity) {
        if (registeredEntities.containsKey(entity)) {
            return Optional.of(registeredEntities.get(entity).getFirst());
        } else {
            return Optional.empty();
        }
    }

    public void activateEntity(Entity entity) {
        Component<ProtoEntity, Configuration> component = registeredEntities.remove(entity);
        if (component != null) {
            activeEntities.put(entity, component);
        }
    }

    public void registerEntity(LivingEntity entity, ProtoEntity protoEntity, Configuration configuration) {
        registeredEntities.put(entity, new Component<>(protoEntity, configuration));
    }

    public void removeEntity(Entity entity) {
        registeredEntities.remove(entity);
        tickedEntities.remove(entity);
        activeEntities.remove(entity);
    }

    public void watchProjectile(Projectile projectile, Entity livingEntity) {
        projectileMap.put(projectile, livingEntity);
    }

    public boolean isWatchingProjectile(Projectile projectile) {
        return projectileMap.containsKey(projectile);
    }

    public void unWatchProjectile(Projectile projectile) {
        projectileMap.remove(projectile);
    }

    public Entity getProjectileShooter(Projectile projectile) {
        return projectileMap.get(projectile);
    }
}
