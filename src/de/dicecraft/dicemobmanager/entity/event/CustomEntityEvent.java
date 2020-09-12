package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Pair;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public abstract class CustomEntityEvent extends Event implements Cancellable {

    private final Plugin plugin;
    private final EntityInformation entityInformation;

    public CustomEntityEvent(Pair<Plugin, EntityInformation> pair) {
        this.plugin = pair.getFirst();
        this.entityInformation = pair.getSecond();
    }

    public abstract LivingEntity getEntity();


    public EntityInformation getEntityInformation() {
        return entityInformation;
    }

    public Plugin getPlugin() {
        return plugin;
    }
}
