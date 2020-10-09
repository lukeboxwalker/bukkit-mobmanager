package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public abstract class Event {

    private final LivingEntity entity;
    private final ProtoEntity<? extends Mob> protoEntity;

    public Event(LivingEntity entity, ProtoEntity<? extends Mob> protoEntity) {
        this.entity = entity;
        this.protoEntity = protoEntity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public ProtoEntity<? extends Mob> getProtoEntity() {
        return protoEntity;
    }
}
