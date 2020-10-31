package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnEvent extends Event implements BukkitEventHolder<EntitySpawnEvent> {

    private final EntitySpawnEvent bukkitSpawnEvent;

    public SpawnEvent(final Event event, final EntitySpawnEvent spawnEvent) {
        this(event.getProtoEntity(), spawnEvent);
    }

    public SpawnEvent(final ProtoEntity<?> protoEntity, final EntitySpawnEvent spawnEvent) {
        super(protoEntity);
        this.bukkitSpawnEvent = spawnEvent;
    }

    @Override
    public EntitySpawnEvent getBukkitEvent() {
        return bukkitSpawnEvent;
    }
}
