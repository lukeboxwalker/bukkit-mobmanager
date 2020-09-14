package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.utils.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

/**
 * Event to wrap the bukkit EntityDeathEvent.
 * <p>
 * Called when ever a custom entity dies
 * in a world.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class CustomEntityDeathEvent extends CustomEntityEvent {

    private final EntityDeathEvent entityDeathEvent;

    /**
     * Creates a new CustomEntityDeathEvent.
     * <p>
     * Using the plugin which is responsible for this
     * entity. Holding the original death event to get
     * further information.
     *
     * @param entityDeathEvent the original death event
     * @param component        containing the plugin as well as the entity information
     */
    public CustomEntityDeathEvent(EntityDeathEvent entityDeathEvent, Component<Plugin, EntityInformation> component){
        super(component);
        this.entityDeathEvent = entityDeathEvent;
    }

    public EntityDeathEvent getEntityDeathEvent() {
        return entityDeathEvent;
    }

    @Override
    public LivingEntity getEntity() {
        return entityDeathEvent.getEntity();
    }

    @Override
    public boolean isCancelled() {
        return entityDeathEvent.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.entityDeathEvent.setCancelled(cancelled);
    }
}
