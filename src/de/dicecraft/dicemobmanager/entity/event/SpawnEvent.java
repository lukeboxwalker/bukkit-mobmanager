package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnEvent extends Event {

    private final EntitySpawnEvent spawnEvent;

    public SpawnEvent(Event event, EntitySpawnEvent spawnEvent) {
        this(event.getEntity(), event.getProtoEntity(), spawnEvent);
    }

    public SpawnEvent(LivingEntity entity, ProtoEntity protoEntity, EntitySpawnEvent spawnEvent) {
        super(entity, protoEntity);
        this.spawnEvent = spawnEvent;
    }

    public EntitySpawnEvent getBukkitEvent() {
        return spawnEvent;
    }
}
