package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.event.DamageEvent;
import de.dicecraft.dicemobmanager.entity.event.DeathEvent;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameUpdater;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyManager;
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

    private StrategyManager strategyManager;
    private Map<Attribute, Double> attributes;
    private Equipment equipment;
    private Set<PotionEffect> potionEffects;
    private Set<GoalKey<? extends Mob>> ignoredGoals;
    private List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals;
    private Set<DeathDrop> deathDrops;
    private NameUpdater nameUpdater;
    private EntityType entityType;
    private int level;
    private String name;
    private boolean shouldBurnInDay;

    @Override
    public boolean shouldBurnInDay() {
        return shouldBurnInDay;
    }

    public void setShouldBurnInDay(boolean shouldBurnInDay) {
        this.shouldBurnInDay = shouldBurnInDay;
    }

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
    public Set<GoalKey<? extends Mob>> getIgnoredGoals() {
        return ignoredGoals;
    }

    public void setIgnoredGoals(Set<GoalKey<? extends Mob>> ignoredGoals) {
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

    @Nonnull
    @Override
    public NameUpdater getNameUpdater() {
        return nameUpdater;
    }

    public void setNameUpdater(NameUpdater nameUpdater) {
        this.nameUpdater = nameUpdater;
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
    public void onEntityTick(TickEvent event) {
        strategyManager.getTickStrategies().forEach(strategy -> strategy.play(event));
    }

    @Override
    public void onEntityDamage(DamageEvent event) {
        strategyManager.getDamageStrategies().forEach(strategy -> strategy.play(event));
    }

    @Override
    public void onEntityDeath(DeathEvent event) {
        strategyManager.getDeathStrategies().forEach(strategy -> strategy.play(event));
    }

    @Override
    public void onEntitySpawn(SpawnEvent event) {
        strategyManager.getSpawnStrategies().forEach(strategy -> strategy.play(event));
    }

    @Override
    public void onItemDrop(ItemDropEvent event) {
        strategyManager.getItemDropStrategies().forEach(strategy -> strategy.play(event));
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setStrategyManager(StrategyManager strategyManager) {
        this.strategyManager = strategyManager;
    }
}
