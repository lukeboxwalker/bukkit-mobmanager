package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnEvent extends Event implements BukkitEventHolder<EntitySpawnEvent> {

    private final EntitySpawnEvent spawnEvent;

    public SpawnEvent(Event event, EntitySpawnEvent spawnEvent) {
        this(event.getEntity(), event.getProtoEntity(), spawnEvent);
    }

    public SpawnEvent(LivingEntity entity, ProtoEntity<?> protoEntity, EntitySpawnEvent spawnEvent) {
        super(entity, protoEntity);
        this.spawnEvent = spawnEvent;
    }

    @Override
    public EntitySpawnEvent getBukkitEvent() {
        return spawnEvent;
    }
}
