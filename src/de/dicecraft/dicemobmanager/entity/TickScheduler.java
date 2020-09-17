package de.dicecraft.dicemobmanager.entity;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Schedules the custom entity tick.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class TickScheduler extends BukkitRunnable {

    private final EntityManager manager;

    public TickScheduler(final EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.tickEntities();
    }
}
