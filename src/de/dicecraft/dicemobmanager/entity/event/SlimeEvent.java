package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.SlimeSplitEvent;

public class SlimeEvent extends Event implements BukkitEvent<SlimeSplitEvent> {

    private final SlimeSplitEvent splitEvent;

    public SlimeEvent(Event event, SlimeSplitEvent splitEvent) {
        this(event.getEntity(), event.getProtoEntity(), splitEvent);
    }

    public SlimeEvent(LivingEntity entity, ProtoEntity protoEntity, SlimeSplitEvent splitEvent) {
        super(entity, protoEntity);
        this.splitEvent = splitEvent;
    }

    @Override
    public SlimeSplitEvent getBukkitEvent() {
        return splitEvent;
    }
}
