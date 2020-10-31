package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.entity.Item;

public class ItemDropEvent extends Event {

    private final DeathDrop deathDrop;
    private final Item item;

    public ItemDropEvent(final Event event, final DeathDrop deathDrop, final Item item) {
        this(event.getProtoEntity(), deathDrop, item);
    }

    /**
     * Creates a new ItemDropEvent.
     *
     * @param protoEntity the ProtoEntity that dropped an item.
     * @param deathDrop   the deathDrop that was dropped by the entity
     * @param item        the item that dropped.
     */
    public ItemDropEvent(final ProtoEntity<?> protoEntity, final DeathDrop deathDrop, final Item item) {
        super(protoEntity);
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
