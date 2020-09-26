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
    private static final int DEFAULT_TICK_SPEED = 1;

    private static DiceMobManager instance;
    private static EntityManager entityManager;
    private static TickScheduler scheduler;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;
        entityManager = new EntityManager();

        // register events
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(entityManager), instance);
        Bukkit.getPluginManager().registerEvents(new ConfigEventListener(entityManager), instance);

        // register commands
        CommandManager.registerCommands(instance, entityManager);

        // starting tick scheduler
        scheduler = new TickScheduler(entityManager, instance);
        scheduler.restart(DEFAULT_TICK_SPEED);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        scheduler.cancel();
        entityManager.destroyAll();
    }

    public static DiceMobManager getInstance() {
        return instance;
    }

    public static void restartScheduler(int ticks) {
        scheduler.restart(ticks);
    }

    public static ConfigBuilder configBuilder() {
        return new CustomConfigBuilder();
    }

    public static EntitySpawnFactory createSpawnFactory(Configuration configuration) {
        return new SpawnFactory(entityManager, configuration);
    }

    public static EntitySpawnFactory createSpawnFactory() {
        return new SpawnFactory(entityManager, configBuilder().build());
    }

    public static ProtoBuilder builder() {
        return new ProtoEntityBuilder();
    }

    public static NamespacedKey createNameSpacedKey(String key) {
        return new NamespacedKey(instance, key);
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
