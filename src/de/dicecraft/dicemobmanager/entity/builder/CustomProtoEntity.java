package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.event.DamageEvent;
import de.dicecraft.dicemobmanager.entity.event.DeathEvent;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.event.SlimeEvent;
import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import de.dicecraft.dicemobmanager.entity.strategy.Strategy;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomProtoEntity implements ProtoEntity {

    private Map<Attribute, Double> attributes;
    private Equipment equipment;
    private Set<PotionEffect> potionEffects;
    private Set<GoalKey<Mob>> ignoredGoals;
    private List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals;
    private Strategy<TickEvent> onTickStrategy;
    private Strategy<DamageEvent> onDamageStrategy;
    private Strategy<DeathEvent> onDeathStrategy;
    private Strategy<ItemDropEvent> onItemDropStrategy;
    private Strategy<SpawnEvent> onSpawnStrategy;
    private Strategy<SlimeEvent> onSlimeSplitStrategy;
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
    public Set<GoalKey<Mob>> getIgnoredGoals() {
        return ignoredGoals;
    }

    public void setIgnoredGoals(Set<GoalKey<Mob>> ignoredGoals) {
        this.ignoredGoals = ignoredGoals;
    }

    @Nonnull
    @Override
    public List<PriorityEntry<GoalSupplier<Mob>>> getGoals() {
        return pathfinderGoals;
    }

    public void setGoals(List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals) {
        this.pathfinderGoals = pathfinderGoals;
    }

    public void setOnTickStrategy(Strategy<TickEvent> onTickStrategy) {
        this.onTickStrategy = onTickStrategy;
    }


    public void setOnDamageStrategy(Strategy<DamageEvent> onDamageStrategy) {
        this.onDamageStrategy = onDamageStrategy;
    }


    public void setOnDeathStrategy(Strategy<DeathEvent> onDeathStrategy) {
        this.onDeathStrategy = onDeathStrategy;
    }


    public void setOnItemDropStrategy(Strategy<ItemDropEvent> onItemDropStrategy) {
        this.onItemDropStrategy = onItemDropStrategy;
    }

    public void setOnSlimeSplitStrategy(Strategy<SlimeEvent> onSlimeSplitStrategy) {
        this.onSlimeSplitStrategy = onSlimeSplitStrategy;
    }

    public void setOnSpawnStrategy(Strategy<SpawnEvent> onSpawnStrategy) {
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
    public void onSlimeSplit(SlimeEvent event) {
        onSlimeSplitStrategy.play(event);
    }

    @Override
    public void onEntityTick(TickEvent event) {
        this.onTickStrategy.play(event);
    }

    @Override
    public void onEntityDamage(DamageEvent event) {
        this.onDamageStrategy.play(event);
    }

    @Override
    public void onEntityDeath(DeathEvent event) {
        this.onDeathStrategy.play(event);
    }

    @Override
    public void onEntitySpawn(SpawnEvent event) {
        this.onSpawnStrategy.play(event);
    }

    @Override
    public void onItemDrop(ItemDropEvent event) {
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
