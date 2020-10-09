package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import org.bukkit.entity.Mob;

public interface ItemDropStrategy<T extends Mob> extends Strategy<T> {

    void play(ItemDropEvent dropEvent, T mob);
}
