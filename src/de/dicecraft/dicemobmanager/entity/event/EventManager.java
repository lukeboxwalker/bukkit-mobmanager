package de.dicecraft.dicemobmanager.entity.event;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public interface EventManager {

    void callEvent(@Nonnull CustomEntityEvent event);

    void registerListeners(@Nonnull Listener listener, @Nonnull Plugin plugin);
}
