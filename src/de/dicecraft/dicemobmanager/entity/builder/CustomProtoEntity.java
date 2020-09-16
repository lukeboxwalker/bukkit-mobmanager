package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.event.EntityDropItemEvent;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import de.dicecraft.dicemobmanager.entity.strategy.Strategy;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomProtoEntity implements ProtoEntity {

    private Map<Attribute, Double> attributes;
    private Equipment equipment;
    private Set<PotionEffect> potionEffects;
    private Set<GoalKey<Mob>> removedGoals;
    private List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals;
    private Strategy<Entity> onTickStrategy;
    private Strategy<EntityDamageEvent> onDamageStrategy;
    private Strategy<EntityDeathEvent> onDeathStrategy;
    private Strategy<EntityDropItemEvent> onItemDropStrategy;
    private Strategy<EntitySpawnEvent> onSpawnStrategy;
    private Set<DeathDrop> deathDrops;
    private NameSupplier nameSupplier;
    private EntityType entityType;
    private int level;
    private String name;

    @Nonnull
    @Override
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Nonnull
    @Override
    public Set<DeathDrop> getDeathDrops() {
        return deathDrops;
    }

    public void setDeathDrops(Set<DeathDrop> deathDrops) {
        this.deathDrops = deathDrops;
    }

    @Nonnull
    @Override
    public Set<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(Set<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    @Nonnull
    @Override
    public Set<GoalKey<Mob>> getRemovedGoals() {
        return removedGoals;
    }

    public void setRemovedGoals(Set<GoalKey<Mob>> removedGoals) {
        this.removedGoals = removedGoals;
    }

    @Nonnull
    @Override
    public List<PriorityEntry<GoalSupplier<Mob>>> getGoals() {
        return pathfinderGoals;
    }

    public void setGoals(List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals) {
        this.pathfinderGoals = pathfinderGoals;
    }

    public void setOnTickStrategy(Strategy<Entity> onTickStrategy) {
        this.onTickStrategy = onTickStrategy;
    }


    public void setOnDamageStrategy(Strategy<EntityDamageEvent> onDamageStrategy) {
        this.onDamageStrategy = onDamageStrategy;
    }


    public void setOnDeathStrategy(Strategy<EntityDeathEvent> onDeathStrategy) {
        this.onDeathStrategy = onDeathStrategy;
    }


    public void setOnItemDropStrategy(Strategy<EntityDropItemEvent> onItemDropStrategy) {
        this.onItemDropStrategy = onItemDropStrategy;
    }


    public void setOnSpawnStrategy(Strategy<EntitySpawnEvent> onSpawnStrategy) {
        this.onSpawnStrategy = onSpawnStrategy;
    }

    @Nonnull
    @Override
    public NameSupplier getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(NameSupplier nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    @Nonnull
    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Nonnull
    @Override
    public Map<Attribute, Double> getAttributeMap() {
        return attributes;
    }

    public void setAttributeMap(Map<Attribute, Double> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void onEntityTick(Entity entity) {
        this.onTickStrategy.play(entity);
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        this.onDamageStrategy.play(event);
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        this.onDeathStrategy.play(event);
    }

    @Override
    public void onEntitySpawn(EntitySpawnEvent event) {
        this.onSpawnStrategy.play(event);
    }

    @Override
    public void onItemDrop(EntityDropItemEvent event) {
        this.onItemDropStrategy.play(event);
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
