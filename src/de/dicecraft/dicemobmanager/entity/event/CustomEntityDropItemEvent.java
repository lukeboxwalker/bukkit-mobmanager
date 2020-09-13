package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomEntityDropItemEvent extends CustomEntityEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final LivingEntity livingEntity;

    private boolean cancelled;

    public CustomEntityDropItemEvent(LivingEntity livingEntity, Component<Plugin, EntityInformation> component){
        super(component);
        this.livingEntity = livingEntity;
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
}
