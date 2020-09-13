package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.Component;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

public class CustomEntityDropItemEvent extends CustomEntityEvent {

    private final LivingEntity livingEntity;
    private final DeathDrop deathDrop;

    private boolean cancelled;

    public CustomEntityDropItemEvent(LivingEntity livingEntity, DeathDrop deathDrop, Component<Plugin, EntityInformation> component){
        super(component);
        this.livingEntity = livingEntity;
        this.deathDrop = deathDrop;
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

    public DeathDrop getDeathDrop() {
        return deathDrop;
    }
}
