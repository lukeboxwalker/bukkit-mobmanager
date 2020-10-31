package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.entity.Mob;

public abstract class Event {

    private final ProtoEntity<? extends Mob> protoEntity;

    public Event(final ProtoEntity<? extends Mob> protoEntity) {
        this.protoEntity = protoEntity;
    }

    public ProtoEntity<? extends Mob> getProtoEntity() {
        return protoEntity;
    }
}
