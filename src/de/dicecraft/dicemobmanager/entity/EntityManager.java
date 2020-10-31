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

    private boolean itemsDrop = true;

    public boolean canItemsDrop() {
        return itemsDrop;
    }

    public void setItemsDrop(final boolean itemsDrop) {
        this.itemsDrop = itemsDrop;
    }

    public void destroyAll() {
        registeredEntities.keySet().forEach(Entity::remove);
        tickedEntities.keySet().forEach(Entity::remove);
    }

    /**
     * Collects all active entities.
     * <p>
     * Notice that an entity in the result map may or may not has been ticked for
     * the first time in there life span.
     *
     * @param entities the entities to filter when collecting to the result map.
     * @return a map of entities in relation to there ProtoEntity.
     */
    public Map<Entity, ProtoEntity<?>> getAllEntities(final Entity... entities) {
        final Map<Entity, ProtoEntity<?>> result = new HashMap<>();
        if (entities.length == 0) {
            activeEntities.forEach((entity, managedEntity) ->
                    result.put(entity, managedEntity.protoEntity));
            tickedEntities.forEach((entity, tickedEntity) ->
                    result.put(entity, tickedEntity.tickEvent.getProtoEntity()));
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

    /**
     * Gets the stored ProtoEntity for given Entity.
     * <p>
     * The ProtoEntity for the given entity may or may not be present.
     * Notice that an ProtoEntity in the result map may or may not has been ticked for
     * the first time in there life span.
     *
     * @param entity the entity to lookup.
     * @return an optional of the ProtoEntity. It will ne empty when the ProtoEntity did not exist.
     */
    public Optional<ProtoEntity<?>> getProtoEntity(final Entity entity) {
        if (tickedEntities.containsKey(entity)) {
            final TickedEntity tickedEntity = tickedEntities.get(entity);
            return Optional.of(tickedEntity.tickEvent.getProtoEntity());
        } else if (activeEntities.containsKey(entity)) {
            return Optional.of(activeEntities.get(entity).protoEntity);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Gets the stored ProtoEntity for given Entity.
     * <p>
     * The Configuration for the given entity may or may not be present.
     * Notice that an Configuration may effect multiple entities.
     *
     * @param entity the entity to lookup.
     * @return an optional of the Configuration. It will ne empty when the ProtoEntity did not exist.
     */
    public Optional<Configuration> getEntityConfig(final Entity entity) {
        if (tickedEntities.containsKey(entity)) {
            final TickedEntity tickedEntity = tickedEntities.get(entity);
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

    /**
     * Ticks the EntityManager.
     * <p>
     * Ticking all entities that can be ticked and ticking dropped items
     * to check when to free item from owner.
     */
    public void tick() {
        tickedEntities.forEach((key, tickedEntity) -> {
            final TickEvent tickEvent = tickedEntity.tickEvent;
            final LivingEntity entity = (LivingEntity) key;
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

        // tick dropped items
        synchronized (droppedItems) {
            for (final Iterator<Item> iterator = droppedItems.iterator(); iterator.hasNext(); ) {
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

    /**
     * Adds an item the manager should be watching.
     *
     * @param item the item to watch.
     */
    public void watchItem(final Item item) {
        synchronized (droppedItems) {
            droppedItems.add(item);
        }
    }

    /**
     * Checks if the entity can be activated.
     *
     * @param entity the entity to check
     * @return an optional of the ProtoEntity that can be activated.
     */
    public Optional<ProtoEntity<?>> canActivateEntity(final Entity entity) {
        if (registeredEntities.containsKey(entity)) {
            return Optional.of(registeredEntities.get(entity).protoEntity);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Activates an entity.
     * <p>
     * Should be called when entity spawns into world.
     *
     * @param entity the entity to activate.
     */
    public void activateEntity(final Entity entity) {
        final ManagedEntity managedEntity = registeredEntities.remove(entity);
        if (managedEntity != null) {
            activeEntities.put(entity, managedEntity);
        }
    }

    public void registerEntity(final LivingEntity entity, final ProtoEntity<?> protoEntity,
                               final Configuration configuration) {
        registeredEntities.put(entity, new ManagedEntity(protoEntity, configuration));
    }

    /**
     * Removes an entity that is managed by this manager.
     *
     * @param entity to remove
     */
    public void removeEntity(final Entity entity) {
        registeredEntities.remove(entity);
        tickedEntities.remove(entity);
        activeEntities.remove(entity);
    }

    public void watchProjectile(final Projectile projectile, final Entity livingEntity) {
        projectileMap.put(projectile, livingEntity);
    }

    public boolean isWatchingProjectile(final Projectile projectile) {
        return projectileMap.containsKey(projectile);
    }

    public void unWatchProjectile(final Projectile projectile) {
        projectileMap.remove(projectile);
    }

    public Entity getProjectileShooter(final Projectile projectile) {
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
