package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.entity.LivingEntity;

public abstract class Event {

    private final LivingEntity entity;
    private final ProtoEntity protoEntity;

    public Event(LivingEntity entity, ProtoEntity protoEntity) {
        this.entity = entity;
        this.protoEntity = protoEntity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public ProtoEntity getProtoEntity() {
        return protoEntity;
    }
}
