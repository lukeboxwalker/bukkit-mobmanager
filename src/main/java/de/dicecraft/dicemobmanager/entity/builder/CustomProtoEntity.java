package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.event.DamageEvent;
import de.dicecraft.dicemobmanager.entity.event.DeathEvent;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import de.dicecraft.dicemobmanager.entity.goals.GoalManager;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.entity.goals.MobGoalManager;
import de.dicecraft.dicemobmanager.entity.name.NameUpdater;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyManager;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomProtoEntity<T extends Mob> implements ProtoEntity<T> {

    private final GoalManager goalManager;

    private StrategyManager<T> strategyManager;
    private Map<Attribute, Double> attributes;
    private Equipment equipment;
    private Set<PotionEffect> potionEffects;
    private Set<DeathDrop> deathDrops;
    private NameUpdater nameUpdater;
    private CustomType<T> entityType;
    private int level;
    private String name;
    private boolean burnInDay;

    public CustomProtoEntity() {
        this.goalManager = new MobGoalManager();
    }

    @Override
    public boolean shouldBurnInDay() {
        return burnInDay;
    }

    public void setShouldBurnInDay(final boolean burnInDay) {
        this.burnInDay = burnInDay;
    }

    @Nonnull
    @Override
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(final Equipment equipment) {
        this.equipment = equipment;
    }

    @Nonnull
    @Override
    public Set<DeathDrop> getDeathDrops() {
        return deathDrops;
    }

    public void setDeathDrops(final Set<DeathDrop> deathDrops) {
        this.deathDrops = deathDrops;
    }

    @Nonnull
    @Override
    public Set<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(final Set<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    @Nonnull
    @Override
    public Set<GoalKey<? extends Mob>> getIgnoredGoals() {
        return goalManager.getIgnoredGoals();
    }

    public void setIgnoredGoals(final Set<GoalKey<? extends Mob>> ignoredGoals) {
        goalManager.ignoreAllGoals(ignoredGoals);
    }

    @Nonnull
    @Override
    public List<PriorityEntry<GoalSupplier<Mob>>> getGoals() {
        return goalManager.getCustomGoals();
    }

    public void setGoals(final List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals) {
        goalManager.addAllCustomGoal(pathfinderGoals);
    }

    @Nonnull
    @Override
    public NameUpdater getNameUpdater() {
        return nameUpdater;
    }

    public void setNameUpdater(final NameUpdater nameUpdater) {
        this.nameUpdater = nameUpdater;
    }

    @Nonnull
    @Override
    public CustomType<T> getCustomType() {
        return entityType;
    }

    @Nonnull
    @Override
    public Map<Attribute, Double> getAttributeMap() {
        return attributes;
    }

    public void setAttributeMap(final Map<Attribute, Double> attributes) {
        this.attributes = attributes;
    }

    @Override
    public void onEntityTick(final TickEvent event, final T mob) {
        strategyManager.getTickStrategies().forEach(strategy -> strategy.play(event, mob));
    }

    @Override
    public void onEntityDamage(final DamageEvent event, final T mob) {
        strategyManager.getDamageStrategies().forEach(strategy -> strategy.play(event, mob));
    }

    @Override
    public void onEntityDeath(final DeathEvent event, final T mob) {
        strategyManager.getDeathStrategies().forEach(strategy -> strategy.play(event, mob));
    }

    @Override
    public void onEntitySpawn(final SpawnEvent event, final T mob) {
        strategyManager.getSpawnStrategies().forEach(strategy -> strategy.play(event, mob));
    }

    @Override
    public void onItemDrop(final ItemDropEvent event, final T mob) {
        strategyManager.getItemDropStrategies().forEach(strategy -> strategy.play(event, mob));
    }

    public void setEntityType(final CustomType<T> entityType) {
        this.entityType = entityType;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(final int level) {
        this.level = level;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    public void setStrategyManager(final StrategyManager<T> strategyManager) {
        this.strategyManager = strategyManager;
    }
}
