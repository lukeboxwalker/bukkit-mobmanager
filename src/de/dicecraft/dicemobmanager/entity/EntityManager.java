package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.builder.CustomEntity;
import de.dicecraft.dicemobmanager.entity.name.NameChangeListener;

import de.dicecraft.dicemobmanager.utils.Component;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

public class EntityManager {

    private final Map<Entity, Component<Plugin, CustomEntity>> registeredEntities = new HashMap<>();
    private final Map<Entity, Component<Plugin, CustomEntity>> activeEntities = new HashMap<>();
    private Map<Entity, Component<Plugin, CustomEntity>> tempoActiveEntities = new HashMap<>();

    private final NameChangeListener nameChangeListener = new NameChangeListener();

    public EntityManager(DiceMobManager plugin) {
        DiceMobManager.getEventManager().registerListener(nameChangeListener, plugin);
    }

    public void destroyAll() {
        registeredEntities.keySet().forEach(Entity::remove);
        activeEntities.keySet().forEach(Entity::remove);
    }

    public Optional<Component<Plugin, CustomEntity>> getInformation(Entity entity) {
        Component<Plugin, CustomEntity> component = activeEntities.get(entity);
        if (component != null) {
            return Optional.of(component);
        } else {
            return Optional.empty();
        }
    }

    public void tickEntities() {
        activeEntities.forEach((entity, component) -> component.getSecond().onEntityTick(entity));
        activeEntities.putAll(tempoActiveEntities);
        tempoActiveEntities = new HashMap<>();
    }

    public boolean activateEntity(Entity entity) {
        Component<Plugin, CustomEntity> component = registeredEntities.remove(entity);
        if (component != null) {
            tempoActiveEntities.put(entity, component);
            return true;
        } else {
            return false;
        }
    }

    public void addEntity(LivingEntity entity, CustomEntity customEntity, Plugin plugin) {
        nameChangeListener.setName(entity, customEntity);
        registeredEntities.put(entity, new Component<>(plugin, customEntity));
    }

    public void removeEntity(LivingEntity entity) {
        registeredEntities.remove(entity);
        activeEntities.remove(entity);
        tempoActiveEntities.remove(entity);

    }
}
