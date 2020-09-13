package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.utils.Component;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class CustomEntityDamageEvent extends CustomEntityEvent {

    private final EntityDamageEvent entityDamageEvent;

    public CustomEntityDamageEvent(EntityDamageEvent entityDamageEvent, Component<Plugin, EntityInformation> component) {
        super(component);
        this.entityDamageEvent = entityDamageEvent;
    }

    public EntityDamageEvent getEntityDamageEvent() {
        return entityDamageEvent;
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
