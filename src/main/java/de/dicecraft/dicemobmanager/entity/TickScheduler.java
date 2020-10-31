package de.dicecraft.dicemobmanager.entity;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Schedules the custom entity tick.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class TickScheduler {

    private final EntityManager manager;
    private final Plugin plugin;
    private BukkitRunnable runnable;

    public TickScheduler(final EntityManager manager, final Plugin plugin) {
        this.manager = manager;
        this.plugin = plugin;
    }

    /**
     * Restarts the scheduler with given tick period.
     *
     * @param ticks the tick period.
     */
    public void restart(final int ticks) {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                manager.tick();
            }
        };
        runnable.runTaskTimer(plugin, 0, ticks);
    }

    /**
     * Cancels the scheduler.
     */
    public void cancel() {
        if (runnable != null) {
            runnable.cancel();
        }
    }
}
