package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.CustomEquipment;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.entity.name.CustomNameUpdater;
import de.dicecraft.dicemobmanager.entity.name.NameUpdater;
import de.dicecraft.dicemobmanager.entity.strategy.SimpleStrategyManager;
import de.dicecraft.dicemobmanager.entity.strategy.Strategy;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyManager;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.attribute.Attribute;
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

public class ProtoEntityBuilder<T extends Mob> implements ProtoBuilder<T> {

    private final Map<Attribute, Double> attributes = new HashMap<>();
    private final Equipment equipment = new CustomEquipment();
    private final Set<PotionEffect> potionEffects = new HashSet<>();
    private final Set<GoalKey<? extends Mob>> ignoredGoals = new HashSet<>();
    private final List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals = new ArrayList<>();
    private final List<Strategy<? super T>> strategies = new ArrayList<>();
    private final CustomType<T> entityType;

    private Set<DeathDrop> deathDrops = new HashSet<>();
    private NameUpdater nameUpdater;
    private int level;
    private String name;
    private boolean shouldBurnInDay = false;

    public ProtoEntityBuilder(final CustomType<T> customType) {
        this.entityType = customType;
    }

    @Override
    public ProtoBuilder<T> setShouldBurnInDay(boolean shouldBurnInDay) {
        this.shouldBurnInDay = shouldBurnInDay;
        return this;
    }

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
    public ProtoBuilder<T> setAttribute(@Nonnull Attribute attribute, double value) {
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
    public ProtoBuilder<T> addEffect(@Nonnull PotionEffect potionEffect) {
        potionEffects.add(potionEffect);
        return this;
    }

    /**
     * Specifies a pathfinder goal.
     * <p>
     * Using a {@link GoalSupplier} to ensure
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
    public ProtoBuilder<T> addGoal(int priority, @Nonnull GoalSupplier<Mob> supplier) {
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
    public ProtoBuilder<T> ignoreGoal(@Nonnull GoalKey<? extends Mob> goalKey) {
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
    public ProtoBuilder<T> addEquipment(@Nonnull EquipmentSlot slot, @Nonnull ItemStack itemStack) {
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
            default:
                return this;
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
    public ProtoBuilder<T> setName(String name) {
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
    public ProtoBuilder<T> setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public ProtoBuilder<T> setDeathDrops(@Nonnull Set<DeathDrop> deathDrops) {
        this.deathDrops = deathDrops;
        return this;
    }


    /**
     * Sets the name supplier.
     * <p>
     * Used to create the displayed name string
     * for the entity. The default name supplier
     * {@link CustomNameUpdater} is used if its not overridden.
     *
     * @param nameUpdater the name supplier
     * @return builder to continue
     */
    @Override
    public ProtoBuilder<T> setNameSupplier(@Nonnull NameUpdater nameUpdater) {
        this.nameUpdater = nameUpdater;
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
    public ProtoBuilder<T> addStrategy(@Nonnull Strategy<? super T> strategy) {
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
    public ProtoEntity<T> build() {
        final CustomProtoEntity<T> protoEntity = new CustomProtoEntity<>();
        protoEntity.setAttributeMap(attributes);
        protoEntity.setDeathDrops(deathDrops);
        protoEntity.setEquipment(equipment);
        protoEntity.setEntityType(entityType);
        protoEntity.setLevel(level);
        protoEntity.setName(name);
        protoEntity.setNameUpdater(nameUpdater == null ? new CustomNameUpdater(protoEntity) : nameUpdater);
        protoEntity.setPotionEffects(potionEffects);
        protoEntity.setIgnoredGoals(ignoredGoals);
        protoEntity.setGoals(pathfinderGoals);
        protoEntity.setShouldBurnInDay(shouldBurnInDay);

        final StrategyManager<T> strategyManager = new SimpleStrategyManager<>();
        strategyManager.addStrategies(this.strategies);

        protoEntity.setStrategyManager(strategyManager);
        return protoEntity;
    }
}
