package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Schedules the custom entity tick.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class TickScheduler extends BukkitRunnable {

    @Override
    public void run() {
        DiceMobManager.getEntityManager().tickEntities();
    }
}
