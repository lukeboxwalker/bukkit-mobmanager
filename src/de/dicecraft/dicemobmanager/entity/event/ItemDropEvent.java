package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;

public class ItemDropEvent extends Event {

    private final DeathDrop deathDrop;
    private final Item item;

    public ItemDropEvent(Event event, DeathDrop deathDrop, Item item) {
        this(event.getEntity(), event.getProtoEntity(), deathDrop, item);
    }

    public ItemDropEvent(LivingEntity entity, ProtoEntity protoEntity, DeathDrop deathDrop, Item item) {
        super(entity, protoEntity);
        this.deathDrop = deathDrop;
        this.item = item;
    }

    public DeathDrop getDeathDrop() {
        return deathDrop;
    }

    public Item getItem() {
        return item;
    }
}
