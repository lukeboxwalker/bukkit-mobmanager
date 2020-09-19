package de.dicecraft.dicemobmanager.entity.factory;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.event.Event;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;

public class ItemSpawnFactory {

    public static Item spawnDeathDrop(LivingEntity entity, ProtoEntity protoEntity, DeathDrop deathDrop, Location location) {
        Item item = location.getWorld().dropItemNaturally(location, deathDrop.getItemStack().clone());
        protoEntity.onItemDrop(new ItemDropEvent(entity, protoEntity, deathDrop, item));
        return item;
    }

    public static Item spawnDeathDrop(Event event, DeathDrop deathDrop, Location location) {
        Item item = location.getWorld().dropItemNaturally(location, deathDrop.getItemStack().clone());
        event.getProtoEntity().onItemDrop(new ItemDropEvent(event, deathDrop, item));
        return item;
    }
}
