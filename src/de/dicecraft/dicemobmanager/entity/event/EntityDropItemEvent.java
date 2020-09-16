package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.Location;
import org.bukkit.entity.Item;

public class EntityDropItemEvent {

    private final DeathDrop deathDrop;
    private final Item item;

    public EntityDropItemEvent(DeathDrop deathDrop, Item item) {
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
