package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import de.dicecraft.dicemobmanager.entity.strategy.Strategy;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyType;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public interface ProtoBuilder {

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
    ProtoBuilder setAttribute(@Nonnull Attribute attribute, double value);

    /**
     * Specifies an potion effect for the entity.
     * <p>
     * Add a given potion effect to the entity
     *
     * @param potionEffect the potion effect to add
     * @return builder to continue
     */
    ProtoBuilder addEffect(@Nonnull PotionEffect potionEffect);

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
    ProtoBuilder setType(@Nonnull EntityType entityType);

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
    ProtoBuilder addGoal(int priority, @Nonnull GoalSupplier<Mob> supplier);

    /**
     * Specifies a goal which need
     * to be ignored from the bukkit entity.
     *
     * @return builder to continue
     */
    ProtoBuilder ignoreGoal(@Nonnull GoalKey<Mob> goalKey);

    /**
     * Specifies the equipment for the entity.
     *
     * @param slot the slot for the equipment
     * @param itemStack the equipment for the entity
     * @return builder to continue
     */
    ProtoBuilder addEquipment(@Nonnull EquipmentSlot slot, @Nonnull ItemStack itemStack);

    /**
     * Sets the custom name of the entity.
     *
     * @param name the custom name string
     * @return builder to continue
     */
    ProtoBuilder setName(String name);

    /**
     * Sets the custom level of
     * the entity.
     *
     * @param level the custom level
     * @return builder to continue
     */
    ProtoBuilder setLevel(int level);

    /**
     * Sets the entity deathDrops.
     *
     * @param deathDrops the death loot
     * @return builder to continue
     */
    ProtoBuilder setDeathDrops(@Nonnull Set<DeathDrop> deathDrops);

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
    ProtoBuilder setNameSupplier(@Nonnull NameSupplier nameSupplier);

    /**
     * Sets an entity strategy.
     * <p>
     * The {@link Strategy} defines a behavior
     * for the entity
     *
     * @return builder to continue
     */
    ProtoBuilder addStrategy(@Nonnull Strategy strategy);

    /**
     * Builds the ProtoEntity.
     * <p>
     * Using all information given to the builder, it
     * creates a new ProtoEntity.
     *
     * @return new ProtoEntity
     */
    ProtoEntity build();

}
