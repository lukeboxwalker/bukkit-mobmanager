package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathEvent extends Event implements BukkitEventHolder<EntityDeathEvent> {

    private final EntityDeathEvent deathEvent;

    public DeathEvent(Event event, EntityDeathEvent deathEvent) {
        this(event.getProtoEntity(), deathEvent);
    }

    public DeathEvent(ProtoEntity<?> protoEntity, EntityDeathEvent deathEvent) {
        super(protoEntity);
        this.deathEvent = deathEvent;
    }

    @Override
    public EntityDeathEvent getBukkitEvent() {
        return deathEvent;
    }
}
