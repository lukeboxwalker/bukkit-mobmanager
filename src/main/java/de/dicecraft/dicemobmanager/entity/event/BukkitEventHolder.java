package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.Event;

public interface BukkitEventHolder<T extends Event> {

    T getBukkitEvent();

}
