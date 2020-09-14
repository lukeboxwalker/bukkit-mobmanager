package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.utils.Component;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

/**
 * Event to wrap the bukkit EntitySpawnEvent.
 * <p>
 * Called when ever a custom entity spawned
 * in a world.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class CustomEntitySpawnEvent extends CustomEntityEvent {

    private final EntitySpawnEvent entitySpawnEvent;

    /**
     * Creates a new CustomEntitySpawnEvent.
     * <p>
     * Using the plugin which is responsible for this
     * entity. Holding the original spawn event to get
     * further information.
     *
     * @param entitySpawnEvent the original spawn event
     * @param component        containing the plugin as well as the entity information
     */
    public CustomEntitySpawnEvent(EntitySpawnEvent entitySpawnEvent, Component<Plugin, EntityInformation> component) {
        super(component);
        this.entitySpawnEvent = entitySpawnEvent;
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entitySpawnEvent.getEntity();
    }

    @Override
    public boolean isCancelled() {
        return this.entitySpawnEvent.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.entitySpawnEvent.setCancelled(cancelled);
    }

    public EntitySpawnEvent getEntitySpawnEvent() {
        return entitySpawnEvent;
    }
}