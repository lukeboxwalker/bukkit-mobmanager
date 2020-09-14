package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.utils.Component;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

/**
 * Event to broadcast a item drop.
 * <p>
 * Called when ever a custom entity drops
 * an item.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class CustomEntityDropItemEvent extends CustomEntityEvent {

    private final LivingEntity livingEntity;
    private final DeathDrop deathDrop;

    private boolean cancelled;

    /**
     * Creates a new CustomEntityDropItemEvent.
     * <p>
     * Using the plugin which is responsible for this
     * entity. Holding the death drop to get
     * further information.
     *
     * @param livingEntity the entity which will drop an item
     * @param deathDrop    the loot drop
     * @param component    containing the plugin as well as the entity information
     */
    public CustomEntityDropItemEvent(LivingEntity livingEntity, DeathDrop deathDrop, Component<Plugin, EntityInformation> component) {
        super(component);
        this.livingEntity = livingEntity;
        this.deathDrop = deathDrop;
    }

    @Override
    public LivingEntity getEntity() {
        return this.livingEntity;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public DeathDrop getDeathDrop() {
        return deathDrop;
    }
}
