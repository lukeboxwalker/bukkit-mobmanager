package de.dicecraft.dicemobmanager;

import de.dicecraft.dicemobmanager.command.CommandUtils;

import de.dicecraft.dicemobmanager.configuration.ConfigBuilder;
import de.dicecraft.dicemobmanager.configuration.ConfigEventListener;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.configuration.CustomConfigBuilder;
import de.dicecraft.dicemobmanager.entity.TickScheduler;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.event.EntityEventListener;
import de.dicecraft.dicemobmanager.entity.builder.ProtoBuilder;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntityBuilder;
import de.dicecraft.dicemobmanager.entity.factory.EntitySpawnFactory;
import de.dicecraft.dicemobmanager.entity.factory.SpawnFactory;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;
import java.util.logging.Logger;

public class DiceMobManager extends JavaPlugin {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger("DiceMobManager");
    private static final EntityManager ENTITY_MANAGER = new EntityManager();
    private static final int DEFAULT_TICK_SPEED = 1;

    private static DiceMobManager instance;

    private static TickScheduler scheduler;

    @Override
    public void onEnable() {
        super.onEnable();
        instance = this;

        // register events
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(ENTITY_MANAGER), instance);
        Bukkit.getPluginManager().registerEvents(new ConfigEventListener(ENTITY_MANAGER), instance);

        // register commands
        CommandUtils.registerCommands(instance, ENTITY_MANAGER);

        // starting tick scheduler
        scheduler = new TickScheduler(ENTITY_MANAGER, instance);
        scheduler.restart(DEFAULT_TICK_SPEED);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        scheduler.cancel();
        ENTITY_MANAGER.destroyAll();
    }

    public static DiceMobManager getInstance() {
        return instance;
    }

    public static void restartScheduler(final int ticks) {
        scheduler.restart(ticks);
    }

    public static ConfigBuilder configBuilder() {
        return new CustomConfigBuilder();
    }

    public static EntitySpawnFactory createSpawnFactory(final Configuration configuration) {
        return new SpawnFactory(ENTITY_MANAGER, configuration);
    }

    public static EntitySpawnFactory createSpawnFactory() {
        return new SpawnFactory(ENTITY_MANAGER, configBuilder().build());
    }

    public static <T extends Mob> ProtoBuilder<T> builder(final CustomType<T> customType) {
        return new ProtoEntityBuilder<>(customType);
    }

    public static NamespacedKey createNameSpacedKey(final String key) {
        return new NamespacedKey(instance, key);
    }

    public static Logger logging() {
        return LOGGER;
    }

    public static Random getRandom() {
        return RANDOM;
    }

    public static int randomIntBetween(final int min, final int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
