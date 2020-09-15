package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;

/**
 * Builds an Entity.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface EntityBuilder {

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
    EntityBuilder setAttribute(@Nonnull Attribute attribute, double value);

    /**
     * Specifies an potion effect for the entity.
     * <p>
     * Add a given potion effect to the entity
     *
     * @param potionEffect the potion effect to add
     * @return builder to continue
     */
    EntityBuilder addEffect(@Nonnull PotionEffect potionEffect);

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
    EntityBuilder setType(@Nonnull EntityType entityType);

    /**
     * Specifies the location for the entity.
     *
     * @param location the location for the entity
     * @return builder to continue
     */
    EntityBuilder setLocation(@Nonnull Location location);

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
    EntityBuilder addGoal(int priority, @Nonnull GoalSupplier<Mob> supplier);

    /**
     * Specifies the custom entity information for the entity.
     *
     * @param customEntity the world for the entity
     * @return builder to continue
     */
    EntityBuilder setCustomEntity(@Nonnull CustomEntity customEntity);

    /**
     * Specifies the equipment for the entity.
     *
     * @param equipment the equipment for the entity
     * @return builder to continue
     */
    EntityBuilder setEquipment(@Nonnull Equipment equipment);

    /**
     * Builds the Entity.
     * <p>
     * Using all information given to the builder, it
     * creates a new CustomEntity.
     *
     * @return new CustomEntity
     * @throws EntityCreationException when creation fails
     */
    Entity buildAndSpawn() throws EntityCreationException;
}
