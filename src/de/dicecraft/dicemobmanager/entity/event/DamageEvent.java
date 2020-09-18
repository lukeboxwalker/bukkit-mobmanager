package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent extends Event implements BukkitEventHolder<EntityDamageEvent> {

    private final EntityDamageEvent damageEvent;

    public DamageEvent(Event event, EntityDamageEvent damageEvent) {
        this(event.getEntity(), event.getProtoEntity(), damageEvent);
    }

    public DamageEvent(LivingEntity entity, ProtoEntity protoEntity, EntityDamageEvent damageEvent) {
        super(entity, protoEntity);
        this.damageEvent = damageEvent;
    }

    @Override
    public EntityDamageEvent getBukkitEvent() {
        return damageEvent;
    }
}
