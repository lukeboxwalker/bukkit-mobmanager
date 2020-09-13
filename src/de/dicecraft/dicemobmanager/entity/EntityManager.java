package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.name.NameChangeListener;

import de.dicecraft.dicemobmanager.utils.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityManager {

    private final Map<Plugin, Map<Entity, EntityInformation>> entities = new HashMap<>();
    private final NameChangeListener nameChangeListener = new NameChangeListener();


    public EntityManager(DiceMobManager plugin) {
        DiceMobManager.getEventManager().registerListener(nameChangeListener, plugin);
    }

    public void destroyAll() {
        entities.values().stream().map(Map::keySet).flatMap(Collection::stream).forEach(Entity::remove);
    }

    public Optional<Component<Plugin, EntityInformation>> getInformation(Entity entity) {
        for (Map.Entry<Plugin, Map<Entity, EntityInformation>> entry : entities.entrySet()) {
            if (entry.getValue().containsKey(entity)) {
                return Optional.of(new Component<>(entry.getKey(), entry.getValue().get(entity)));
            }
        }
        return Optional.empty();
    }

    public void addEntity(LivingEntity entity, EntityInformation entityInformation, Plugin plugin) {
        nameChangeListener.setName(entity, entityInformation);
        if (entities.containsKey(plugin)) {
            entities.get(plugin).put(entity, entityInformation);
        } else {
            Map<Entity, EntityInformation> map = new HashMap<>();
            map.put(entity, entityInformation);
            entities.put(plugin, map);
        }
    }

    public void removeEntity(LivingEntity entity, Plugin plugin) {
        entities.get(plugin).remove(entity);
    }
}
