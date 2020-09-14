package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.utils.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.Plugin;

/**
 * Event to transfer a state change on
 * a custom entity.
 * <p>
 * All custom entity event are cancellable
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public abstract class CustomEntityEvent implements Cancellable {

    private final Plugin plugin;
    private final EntityInformation entityInformation;

    /**
     * Creates a new CustomEntityEvent.
     * <p>
     * Using the plugin which is responsible for this
     * entity.
     *
     * @param component containing the plugin as well as the entity information
     */
    public CustomEntityEvent(Component<Plugin, EntityInformation> component) {
        this.plugin = component.getFirst();
        this.entityInformation = component.getSecond();
    }

    public abstract LivingEntity getEntity();

    public EntityInformation getEntityInformation() {
        return entityInformation;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
