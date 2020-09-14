package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.CustomEntity;
import de.dicecraft.dicemobmanager.utils.Component;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

/**
 * Event to wrap the bukkit EntityDamageEvent.
 * <p>
 * Called when ever a custom entity was damaged
 * in a world.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class CustomEntityDamageEvent extends CustomEntityEvent {

    private final EntityDamageEvent entityDamageEvent;

    /**
     * Creates a new CustomEntityDamageEvent.
     * <p>
     * Using the plugin which is responsible for this
     * entity. Holding the original damage event to get
     * further information.
     *
     * @param entityDamageEvent the original damage event
     * @param component        containing the plugin as well as the entity information
     */
    public CustomEntityDamageEvent(EntityDamageEvent entityDamageEvent, Component<Plugin, CustomEntity> component) {
        super(component);
        this.entityDamageEvent = entityDamageEvent;
    }

    public EntityDamageEvent getEntityDamageEvent() {
        return entityDamageEvent;
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entityDamageEvent.getEntity();
    }

    @Override
    public boolean isCancelled() {
        return entityDamageEvent.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.entityDamageEvent.setCancelled(cancelled);
    }
}
