package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.Event;

public interface BukkitEvent<T extends Event> {

    T getBukkitEvent();

}
