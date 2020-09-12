package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Pair;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomEntitySpawnEvent extends CustomEntityEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final EntitySpawnEvent entitySpawnEvent;

    public CustomEntitySpawnEvent(EntitySpawnEvent entitySpawnEvent, Pair<Plugin, EntityInformation> pair) {
        super(pair);
        this.entitySpawnEvent = entitySpawnEvent;
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) entitySpawnEvent.getEntity();
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    @Nonnull
    public HandlerList getHandlers() {
        return HANDLER_LIST;
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