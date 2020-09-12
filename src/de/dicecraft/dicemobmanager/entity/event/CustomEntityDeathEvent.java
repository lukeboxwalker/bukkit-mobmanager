package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Pair;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomEntityDeathEvent extends CustomEntityEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final EntityDeathEvent entityDeathEvent;

    public CustomEntityDeathEvent(EntityDeathEvent entityDeathEvent, Pair<Plugin, EntityInformation> pair){
        super(pair);
        this.entityDeathEvent = entityDeathEvent;
    }

    public EntityDeathEvent getEntityDeathEvent() {
        return entityDeathEvent;
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
