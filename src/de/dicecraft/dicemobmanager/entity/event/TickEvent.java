package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;

public class TickEvent extends Event {

    public TickEvent(ProtoEntity<?> protoEntity) {
        super(protoEntity);
    }
}
