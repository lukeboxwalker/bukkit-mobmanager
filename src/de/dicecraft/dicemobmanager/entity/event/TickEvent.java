package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.entity.LivingEntity;

public class TickEvent extends Event {

    public TickEvent(LivingEntity entity, ProtoEntity<?> protoEntity) {
        super(entity, protoEntity);
    }
}
