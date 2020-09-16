package de.dicecraft.dicemobmanager;

import de.dicecraft.dicemobmanager.command.CommandManager;

import de.dicecraft.dicemobmanager.entity.TickScheduler;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.event.EntityEventListener;
import de.dicecraft.dicemobmanager.entity.builder.ProtoBuilder;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntityBuilder;
import de.dicecraft.dicemobmanager.entity.factory.EntitySpawnFactory;
import de.dicecraft.dicemobmanager.entity.factory.SpawnFactory;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class DiceMobManager extends JavaPlugin {

    private static final Random RANDOM = new Random();

    private static DiceMobManager INSTANCE;
    private static EntityManager ENTITY_MANAGER;
    private static TickScheduler SCHEDULER;

    @Override
    public void onEnable() {
        super.onEnable();
        INSTANCE = this;
        ENTITY_MANAGER = new EntityManager();
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(), this);
        CommandManager.registerCommands(this);
        restartScheduler(10);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        SCHEDULER.cancel();
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

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER;
    }

    public static EntitySpawnFactory createSpawnFactory() {
        return new SpawnFactory();
    }

    public static ProtoBuilder builder() {
        return new ProtoEntityBuilder();
    }

    public static NamespacedKey createNameSpacedKey(String key) {
        return new NamespacedKey(INSTANCE, key);
    }

    public static int randomIntBetween(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
