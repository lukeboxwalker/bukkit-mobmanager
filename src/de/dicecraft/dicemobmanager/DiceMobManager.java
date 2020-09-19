package de.dicecraft.dicemobmanager;

import de.dicecraft.dicemobmanager.command.CommandManager;

import de.dicecraft.dicemobmanager.configuration.ConfigBuilder;
import de.dicecraft.dicemobmanager.configuration.ConfigEventListener;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.configuration.CustomConfigBuilder;
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
import java.util.logging.Logger;

public class DiceMobManager extends JavaPlugin {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger("DiceMobManager");
    private static final int DEFAULT_TICK_SPEED = 10;

    private static DiceMobManager INSTANCE;
    private static EntityManager ENTITY_MANAGER;
    private static TickScheduler SCHEDULER;

    @Override
    public void onEnable() {
        super.onEnable();
        INSTANCE = this;
        ENTITY_MANAGER = new EntityManager();
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(ENTITY_MANAGER), this);
        Bukkit.getPluginManager().registerEvents(new ConfigEventListener(ENTITY_MANAGER), this);
        CommandManager.registerCommands(this);
        restartScheduler(DEFAULT_TICK_SPEED);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        SCHEDULER.cancel();
        ENTITY_MANAGER.destroyAll();
    }

    public static void restartScheduler(int ticks) {
        if (SCHEDULER != null) SCHEDULER.cancel();
        SCHEDULER = new TickScheduler(ENTITY_MANAGER);
        SCHEDULER.runTaskTimer(INSTANCE, 0, ticks);
    }

    public static DiceMobManager getInstance() {
        return INSTANCE;
    }

    public static ConfigBuilder configBuilder() {
        return new CustomConfigBuilder();
    }

    public static EntitySpawnFactory createSpawnFactory(Configuration configuration) {
        return new SpawnFactory(ENTITY_MANAGER, configuration);
    }

    public static EntitySpawnFactory createSpawnFactory() {
        return new SpawnFactory(ENTITY_MANAGER, configBuilder().build());
    }

    public static ProtoBuilder builder() {
        return new ProtoEntityBuilder();
    }

    public static NamespacedKey createNameSpacedKey(String key) {
        return new NamespacedKey(INSTANCE, key);
    }

    public static Logger logger() {
        return LOGGER;
    }

    public static Random getRandom() {
        return RANDOM;
    }

    public static int randomIntBetween(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
