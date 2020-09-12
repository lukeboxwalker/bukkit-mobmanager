package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.builder.CustomEntityBuilder;
import de.dicecraft.dicemobmanager.entity.builder.EntityBuilder;
import de.dicecraft.dicemobmanager.entity.name.NameChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomEntities {

    private static final Map<Plugin, Map<Entity, EntityInformation>> ENTITIES = new HashMap<>();
    private static final NameChangeListener NAME_CHANGE_LISTENER = new NameChangeListener();

    static {
        Bukkit.getPluginManager().registerEvents(NAME_CHANGE_LISTENER, DiceMobManager.getInstance());
    }

    public static CustomEntityBuilder builder(Plugin plugin) {
        return new EntityBuilder(plugin);

    }

    public static Optional<EntityInformation> getInformation(Entity entity) {
        for (Map.Entry<Plugin, Map<Entity, EntityInformation>> entry : ENTITIES.entrySet()) {
            if (entry.getValue().containsKey(entity)) {
                return Optional.ofNullable(entry.getValue().get(entity));
            }
        }
        return Optional.empty();
    }

    public static void addEntity(LivingEntity entity, EntityInformation entityInformation, Plugin plugin) {
        NAME_CHANGE_LISTENER.setName(entity, entityInformation);
        if (ENTITIES.containsKey(plugin)) {
            ENTITIES.get(plugin).put(entity, entityInformation);
        } else {
            Map<Entity, EntityInformation> map = new HashMap<>();
            map.put(entity, entityInformation);
            ENTITIES.put(plugin, map);
        }
    }

    public static void removeEntity(LivingEntity entity, Plugin plugin) {
        if (ENTITIES.containsKey(plugin)) {
            ENTITIES.get(plugin).remove(entity);
        }
    }
}
