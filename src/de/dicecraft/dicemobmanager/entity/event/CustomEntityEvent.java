package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.plugin.Plugin;

public abstract class CustomEntityEvent implements Event, Cancellable {

    private final Plugin plugin;
    private final EntityInformation entityInformation;

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
