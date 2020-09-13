package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Component;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

public class CustomEntitySpawnEvent extends CustomEntityEvent {

    private final EntitySpawnEvent entitySpawnEvent;

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