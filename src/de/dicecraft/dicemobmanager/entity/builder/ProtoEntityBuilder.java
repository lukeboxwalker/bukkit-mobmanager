package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.CustomEquipment;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.event.DamageEvent;
import de.dicecraft.dicemobmanager.entity.event.DeathEvent;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.event.SlimeEvent;
import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import de.dicecraft.dicemobmanager.entity.strategy.SimpleStrategyManager;
import de.dicecraft.dicemobmanager.entity.strategy.Strategy;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyManager;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyType;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProtoEntityBuilder implements ProtoBuilder {

    private final Map<Attribute, Double> attributes = new HashMap<>();
    private final Equipment equipment = new CustomEquipment();
    private final Set<PotionEffect> potionEffects = new HashSet<>();
    private final Set<GoalKey<Mob>> ignoredGoals = new HashSet<>();
    private final List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals = new ArrayList<>();
    private final List<Strategy> strategies = new ArrayList<>();

    private Set<DeathDrop> deathDrops = new HashSet<>();
    private NameSupplier nameSupplier = new CustomNameSupplier();
    private EntityType entityType = EntityType.ZOMBIE;
    private int level;
    private String name;

    /**
     * Specifies an attribute for the entity.
     * <p>
     * All attributes are collected, see {@link Attribute} and take on entity
     * when building. Value should be positive to set custom attribute value.
     * If the value is negative the attribute is considered to use the default
     * value.
     *
     * @param attribute attribute type to set value for
     * @param value     the value for the given attribute
     * @return builder to continue
     */
    @Override
    public ProtoBuilder setAttribute(@Nonnull Attribute attribute, double value) {
        attributes.put(attribute, value);
        return this;
    }

    /**
     * Specifies an potion effect for the entity.
     * <p>
     * Add a given potion effect to the entity
     *
     * @param potionEffect the potion effect to add
     * @return builder to continue
     */
    @Override
    public ProtoBuilder addEffect(@Nonnull PotionEffect potionEffect) {
        potionEffects.add(potionEffect);
        return this;
    }

    /**
     * Specifies the type for the entity.
     * <p>
     * The entity type determines the entity model, as
     * well as the specific class to instantiate when building.
     * See also {@link EntityType}
     *
     * @param entityType type of the entity
     * @return builder to continue
     */
    @Override
    public ProtoBuilder setType(@Nonnull EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * Specifies a pathfinder goal.
     * <p>
     * Using a supplier of {@link Goal} to ensure
     * to provide a unique object for each entity when building.
     * Each goal has a priority to determine the order to use them.
     * The goal selection always prefers lower prioritised pathfinder goals.
     * The highest priority that is possible is 1.
     *
     * @param priority the priority of the goal should be non negative
     * @param supplier to supply pathfinder goals
     * @return builder to continue
     */
    @Override
    public ProtoBuilder addGoal(int priority, @Nonnull GoalSupplier<Mob> supplier) {
        pathfinderGoals.add(new PriorityEntry<>(priority, supplier));
        return this;
    }


    /**
     * Specifies a goal which need
     * to be removed from the bukkit entity.
     *
     * @return builder to continue
     */
    @Override
    public ProtoBuilder ignoreGoal(@Nonnull GoalKey<Mob> goalKey) {
        ignoredGoals.add(goalKey);
        return this;
    }

    /**
     * Specifies the equipment for the entity.
     *
     * @param slot      the slot for the equipment
     * @param itemStack the equipment for the entity
     * @return builder to continue
     */
    @Override
    public ProtoBuilder addEquipment(@Nonnull EquipmentSlot slot, @Nonnull ItemStack itemStack) {
        switch (slot) {
            case FEET:
                equipment.setBoots(itemStack);
                break;
            case HAND:
                equipment.setItemInMainHand(itemStack);
                break;
            case OFF_HAND:
                equipment.setItemInOffHand(itemStack);
                break;
            case HEAD:
                equipment.setHelmet(itemStack);
                break;
            case LEGS:
                equipment.setLeggings(itemStack);
                break;
            case CHEST:
                equipment.setChestPlate(itemStack);
                break;
        }
        return this;
    }

    /**
     * Sets the custom name of the entity.
     *
     * @param name the custom name string
     * @return builder to continue
     */
    @Override
    public ProtoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the custom level of
     * the entity.
     *
     * @param level the custom level
     * @return builder to continue
     */
    @Override
    public ProtoBuilder setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public ProtoBuilder setDeathDrops(@Nonnull Set<DeathDrop> deathDrops) {
        this.deathDrops = deathDrops;
        return this;
    }


    /**
     * Sets the name supplier.
     * <p>
     * Used to create the displayed name string
     * for the entity. The default name supplier
     * {@link CustomNameSupplier} is used if its not overridden.
     *
     * @param nameSupplier the name supplier
     * @return builder to continue
     */
    @Override
    public ProtoBuilder setNameSupplier(@Nonnull NameSupplier nameSupplier) {
        this.nameSupplier = nameSupplier;
        return this;
    }

    /**
     * Sets an entity strategy.
     * <p>
     * The {@link Strategy} defines a behavior
     * for the entity
     *
     * @return builder to continue
     */
    @Override
    public ProtoBuilder addStrategy(@Nonnull Strategy strategy) {
        this.strategies.add(strategy);
        return this;
    }

    /**
     * Builds the ProtoEntity.
     * <p>
     * Using all information given to the builder, it
     * creates a new ProtoEntity.
     *
     * @return new ProtoEntity
     */
    @Override
    public ProtoEntity build() {
        final CustomProtoEntity protoEntity = new CustomProtoEntity();
        protoEntity.setAttributeMap(attributes);
        protoEntity.setDeathDrops(deathDrops);
        protoEntity.setEquipment(equipment);
        protoEntity.setEntityType(entityType);
        protoEntity.setLevel(level);
        protoEntity.setName(name);
        protoEntity.setNameSupplier(nameSupplier);
        protoEntity.setPotionEffects(potionEffects);
        protoEntity.setIgnoredGoals(ignoredGoals);
        protoEntity.setGoals(pathfinderGoals);

        final StrategyManager strategyManager = new SimpleStrategyManager();
        strategyManager.addStrategies(this.strategies);

        protoEntity.setStrategyManager(strategyManager);
        return protoEntity;
    }
}
