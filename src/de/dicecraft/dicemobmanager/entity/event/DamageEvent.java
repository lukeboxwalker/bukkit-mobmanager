package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent extends Event implements BukkitEventHolder<EntityDamageEvent> {

    private final EntityDamageEvent bukkitDamageEvent;

    public DamageEvent(final Event event, final EntityDamageEvent damageEvent) {
        this(event.getProtoEntity(), damageEvent);
    }

    public DamageEvent(final ProtoEntity<?> protoEntity, final EntityDamageEvent damageEvent) {
        super(protoEntity);
        this.bukkitDamageEvent = damageEvent;
    }

    @Override
    public EntityDamageEvent getBukkitEvent() {
        return bukkitDamageEvent;
    }
}
