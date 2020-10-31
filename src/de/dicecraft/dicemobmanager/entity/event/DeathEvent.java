package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathEvent extends Event implements BukkitEventHolder<EntityDeathEvent> {

    private final EntityDeathEvent bukkitDeathEvent;

    public DeathEvent(final Event event, final EntityDeathEvent deathEvent) {
        this(event.getProtoEntity(), deathEvent);
    }

    public DeathEvent(final ProtoEntity<?> protoEntity, final EntityDeathEvent deathEvent) {
        super(protoEntity);
        this.bukkitDeathEvent = deathEvent;
    }

    @Override
    public EntityDeathEvent getBukkitEvent() {
        return bukkitDeathEvent;
    }
}
