package de.dicecraft.dicemobmanager;

import de.dicecraft.dicemobmanager.command.CommandManager;

import de.dicecraft.dicemobmanager.entity.TickScheduler;
import de.dicecraft.dicemobmanager.entity.builder.CustomEntityBuilder;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.builder.EntityBuilder;
import de.dicecraft.dicemobmanager.entity.event.EntityEventListener;
import de.dicecraft.dicemobmanager.entity.event.CustomEventManager;
import de.dicecraft.dicemobmanager.entity.event.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class DiceMobManager extends JavaPlugin {

    private static final Random RANDOM = new Random();

    private static DiceMobManager INSTANCE;
    private static EventManager EVENT_MANAGER;
    private static EntityManager ENTITY_MANAGER;
    private static TickScheduler SCHEDULER;

    @Override
    public void onEnable() {
        super.onEnable();
        INSTANCE = this;
        EVENT_MANAGER = new CustomEventManager(this);
        ENTITY_MANAGER = new EntityManager(this);
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(), this);
        CommandManager.registerCommands(this);
        restartScheduler(10);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getEntityManager().destroyAll();
    }

    public static void restartScheduler(int ticks) {
        if (SCHEDULER != null) SCHEDULER.cancel();
        SCHEDULER = new TickScheduler();
        SCHEDULER.runTaskTimer(INSTANCE, 0, ticks);
    }

    public static DiceMobManager getInstance() {
        return INSTANCE;
    }

    public static EventManager getEventManager() {
        return EVENT_MANAGER;
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER;
    }

    public static EntityBuilder builder(Plugin plugin) {
        return new CustomEntityBuilder(plugin);
    }

    public static NamespacedKey createNameSpacedKey(String key) {
        return new NamespacedKey(INSTANCE, key);
    }

    public static int randomIntBetween(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
