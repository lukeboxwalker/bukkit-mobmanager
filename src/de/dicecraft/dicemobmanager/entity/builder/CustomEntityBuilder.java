package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * Builds an Entity.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface CustomEntityBuilder{

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
    CustomEntityBuilder setAttribute(@Nonnull Attribute attribute, double value);

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
    CustomEntityBuilder fromType(@Nonnull EntityType entityType);

    /**
     * Specifies the location for the entity.
     *
     * @param location the location for the entity
     * @return builder to continue
     */
    CustomEntityBuilder atLocation(@Nonnull Location location);

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
    CustomEntityBuilder attachGoalSelector(int priority, @Nonnull Function<Mob, Goal<Mob>> supplier);

    /**
     * Specifies a pathfinder goal target.
     * <p>
     * Using a supplier of {@link Goal} to ensure
     * to provide a unique object for each entity when building.
     * Each goal target has a priority to determine the order to use them.
     * The target selection always prefers lower prioritised pathfinder goal targets.
     * The highest priority that is possible is 1.
     *
     * @param priority the priority of the goal target should be non negative
     * @param supplier to supply pathfinder goal targets
     * @return builder to continue
     */
    CustomEntityBuilder attachTargetSelector(int priority, @Nonnull Function<Mob, Goal<Mob>> supplier);

    /**
     * Specifies the custom information for the entity.
     *
     * @param information the world for the entity
     * @return builder to continue
     */
    CustomEntityBuilder useInformation(@Nonnull EntityInformation information);

    /**
     * Specifies the equipment for the entity.
     *
     * @param equipment the equipment for the entity
     * @return builder to continue
     */
    CustomEntityBuilder setEquipment(@Nonnull Equipment equipment);

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
