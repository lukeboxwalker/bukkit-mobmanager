package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnEvent extends Event implements BukkitEventHolder<EntitySpawnEvent> {

    private final EntitySpawnEvent spawnEvent;

    public SpawnEvent(Event event, EntitySpawnEvent spawnEvent) {
        this(event.getProtoEntity(), spawnEvent);
    }

    public SpawnEvent(ProtoEntity<?> protoEntity, EntitySpawnEvent spawnEvent) {
        super(protoEntity);
        this.spawnEvent = spawnEvent;
    }

    @Override
    public EntitySpawnEvent getBukkitEvent() {
        return spawnEvent;
    }
}
