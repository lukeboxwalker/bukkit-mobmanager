package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.configuration.Configuration;

import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import de.dicecraft.dicemobmanager.entity.goals.EntitySelector;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Slime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityManager {

    private static final int TICK_MINUTE = 1200;

    private final Map<Entity, ManagedEntity> registeredEntities = new HashMap<>();
    private final Map<Entity, ManagedEntity> activeEntities = new HashMap<>();
    private final Map<Entity, TickedEntity> tickedEntities = new HashMap<>();
    private final Map<Entity, Entity> projectileMap = new HashMap<>();
    private final Set<Item> droppedItems = new HashSet<>();

    private final List<LivingEntity> deathEntities = new ArrayList<>();
    private final Function<Map.Entry<Entity, ManagedEntity>, TickEvent> toTickEvent =
            entry -> new TickEvent(entry.getValue().protoEntity);

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

    public Map<Entity, ProtoEntity<?>> getAllEntities(Entity... entities) {
        Map<Entity, ProtoEntity<?>> result = new HashMap<>();
        if (entities.length == 0) {
            activeEntities.forEach((entity, managedEntity) -> result.put(entity, managedEntity.protoEntity));
            tickedEntities.forEach((entity, tickedEntity) -> result.put(entity, tickedEntity.tickEvent.getProtoEntity()));
        } else {
            Arrays.stream(entities).forEach(entity -> {
                if (activeEntities.containsKey(entity)) {
                    result.put(entity, activeEntities.get(entity).protoEntity);
                }
                if (tickedEntities.containsKey(entity)) {
                    result.put(entity, tickedEntities.get(entity).tickEvent.getProtoEntity());
                }
            });
        }
        return result;
    }

    public Optional<ProtoEntity<?>> getProtoEntity(Entity entity) {
        if (tickedEntities.containsKey(entity)) {
            TickedEntity tickedEntity = tickedEntities.get(entity);
            return Optional.of(tickedEntity.tickEvent.getProtoEntity());
        } else if (activeEntities.containsKey(entity)) {
            return Optional.of(activeEntities.get(entity).protoEntity);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Configuration> getEntityConfig(Entity entity) {
        if (tickedEntities.containsKey(entity)) {
            TickedEntity tickedEntity = tickedEntities.get(entity);
            return Optional.of(tickedEntity.configuration);
        } else if (activeEntities.containsKey(entity)) {
            return Optional.of(activeEntities.get(entity).configuration);
        } else {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Mob> void tickEntity(final ProtoEntity<T> protoEntity, final Entity entity) {
        protoEntity.onEntityTick(tickedEntities.get(entity).tickEvent, (T) entity);
    }

    public void tick() {
        tickedEntities.forEach((key, tickedEntity) -> {
            TickEvent tickEvent = tickedEntity.tickEvent;
            LivingEntity entity = (LivingEntity) key;
            if (entity.isDead()) {
                // only remove entity on tick if entity is not a slimes with size > 1
                // because the slime will be removed when the slime split event
                // of this entity was called.
                if (!(EntitySelector.IS_SLIME_ENTITY.test(entity) && ((Slime) entity).getSize() > 1)) {
                    deathEntities.add(entity);
                }
            } else {
                // ticking the entity
                tickEntity(tickEvent.getProtoEntity(), entity);
            }
        });

        // removing all death entities (expect slimes)
        deathEntities.forEach(tickedEntities::remove);
        deathEntities.clear();

        // add waiting entities to ticking map so they can be
        // ticked by this method
        tickedEntities.putAll(activeEntities.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                        new TickedEntity(toTickEvent.apply(entry), entry.getValue().configuration))));
        activeEntities.clear();

        synchronized (droppedItems) {
            for (Iterator<Item> iterator = droppedItems.iterator(); iterator.hasNext(); ) {
                final Item item = iterator.next();
                if (item.isDead()) {
                    iterator.remove();
                } else if (item.getTicksLived() > TICK_MINUTE) {
                    item.setOwner(null);
                    iterator.remove();
                }
            }
        }
    }

    public void watchItem(final Item item) {
        synchronized (droppedItems) {
            droppedItems.add(item);
        }
    }


    public Optional<ProtoEntity<?>> canActivateEntity(Entity entity) {
        if (registeredEntities.containsKey(entity)) {
            return Optional.of(registeredEntities.get(entity).protoEntity);
        } else {
            return Optional.empty();
        }
    }

    public void activateEntity(Entity entity) {
        ManagedEntity managedEntity = registeredEntities.remove(entity);
        if (managedEntity != null) {
            activeEntities.put(entity, managedEntity);
        }
    }

    public void registerEntity(LivingEntity entity, ProtoEntity<?> protoEntity, Configuration configuration) {
        registeredEntities.put(entity, new ManagedEntity(protoEntity, configuration));
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

    public static class ManagedEntity {
        private final ProtoEntity<?> protoEntity;
        private final Configuration configuration;

        public ManagedEntity(final ProtoEntity<?> protoEntity, final Configuration configuration) {
            this.protoEntity = protoEntity;
            this.configuration = configuration;
        }
    }

    public static class TickedEntity {
        private final TickEvent tickEvent;
        private final Configuration configuration;

        public TickedEntity(final TickEvent tickEvent, final Configuration configuration) {
            this.tickEvent = tickEvent;
            this.configuration = configuration;
        }
    }
}
