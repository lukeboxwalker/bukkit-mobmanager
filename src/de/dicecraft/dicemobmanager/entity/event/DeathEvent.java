package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathEvent extends Event {

    private final EntityDeathEvent deathEvent;

    public DeathEvent(Event event, EntityDeathEvent deathEvent) {
        this(event.getEntity(), event.getProtoEntity(), deathEvent);
    }

    public DeathEvent(LivingEntity entity, ProtoEntity protoEntity, EntityDeathEvent deathEvent) {
        super(entity, protoEntity);
        this.deathEvent = deathEvent;
    }

    public EntityDeathEvent getBukkitEvent() {
        return deathEvent;
    }
}
