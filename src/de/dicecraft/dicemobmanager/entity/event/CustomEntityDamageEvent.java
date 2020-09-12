package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Pair;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomEntityDamageEvent extends CustomEntityEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final EntityDamageEvent entityDamageEvent;

    public CustomEntityDamageEvent(EntityDamageEvent entityDamageEvent, Pair<Plugin, EntityInformation> pair) {
        super(pair);
        this.entityDamageEvent = entityDamageEvent;
    }

    public EntityDamageEvent getEntityDamageEvent() {
        return entityDamageEvent;
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
