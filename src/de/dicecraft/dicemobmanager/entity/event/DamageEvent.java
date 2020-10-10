package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent extends Event implements BukkitEventHolder<EntityDamageEvent> {

    private final EntityDamageEvent damageEvent;

    public DamageEvent(Event event, EntityDamageEvent damageEvent) {
        this(event.getProtoEntity(), damageEvent);
    }

    public DamageEvent(ProtoEntity<?> protoEntity, EntityDamageEvent damageEvent) {
        super(protoEntity);
        this.damageEvent = damageEvent;
    }

    @Override
    public EntityDamageEvent getBukkitEvent() {
        return damageEvent;
    }
}
