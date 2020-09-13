package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomEntityDeathEvent extends CustomEntityEvent {

    private final EntityDeathEvent entityDeathEvent;

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
