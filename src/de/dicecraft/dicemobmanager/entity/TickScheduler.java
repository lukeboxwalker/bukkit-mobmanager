package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.scheduler.BukkitRunnable;

public class TickScheduler extends BukkitRunnable {

    @Override
    public void run() {
        DiceMobManager.getEntityManager().tickEntities();
    }
}
