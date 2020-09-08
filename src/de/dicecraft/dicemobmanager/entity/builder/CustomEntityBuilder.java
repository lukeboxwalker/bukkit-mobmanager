package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.adapter.EntityCreationException;
import de.dicecraft.dicemobmanager.entity.CustomEntity;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.datawatcher.CustomDataWatcher;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoal;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoalTarget;
import org.bukkit.World;

import java.util.function.Supplier;

/**
 * Builds a {@link CustomEntity}.
 *
 * @param <T> specific CustomEntity class
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface CustomEntityBuilder<T extends CustomEntity> {

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
    CustomEntityBuilder<T> setAttribute(final Attribute attribute, final double value);

    /**
     * Specifies the type for the entity.
     * <p>
     * The entity type determines the entity model, as
     * well as the specific class to instantiate when building.
     * See also {@link CustomType}
     *
     * @param customType type of the entity
     * @return builder to continue
     */
    CustomEntityBuilder<T> fromType(final CustomType<T> customType);

    /**
     * Specifies the world for the entity.
     *
     * @param world the world for the entity
     * @return builder to continue
     */
    CustomEntityBuilder<T> inWorld(final World world);

    /**
     * Specifies a data watcher the entity should use.
     * <p>
     * The data watcher is needed to provide type specific data
     * to the custom entity see {@link CustomDataWatcher}
     *
     * @param dataWatcher to install in entity
     * @return builder to continue
     */
    CustomEntityBuilder<T> attachDataWatcher(final CustomDataWatcher dataWatcher);

    /**
     * Specifies a pathfinder goal.
     * <p>
     * Using a supplier of {@link CustomPathfinderGoal} to ensure
     * to provide a unique object for each entity when building.
     * Each goal has a priority to determine the order to use them.
     * The goal selection always prefers lower prioritised pathfinder goals.
     * The highest priority that is possible is 1.
     *
     * @param priority the priority of the goal should be non negative
     * @param supplier to supply pathfinder goals
     * @return builder to continue
     */
    CustomEntityBuilder<T> attachGoalSelector(final int priority, Supplier<CustomPathfinderGoal> supplier);

    /**
     * Specifies a pathfinder goal target.
     * <p>
     * Using a supplier of {@link CustomPathfinderGoalTarget} to ensure
     * to provide a unique object for each entity when building.
     * Each goal target has a priority to determine the order to use them.
     * The target selection always prefers lower prioritised pathfinder goal targets.
     * The highest priority that is possible is 1.
     *
     * @param priority the priority of the goal target should be non negative
     * @param supplier to supply pathfinder goal targets
     * @return builder to continue
     */
    CustomEntityBuilder<T> attachTargetSelector(final int priority, Supplier<CustomPathfinderGoalTarget> supplier);

    /**
     * Builds the {@link CustomEntity}.
     * <p>
     * Using all information given to the builder, it
     * creates a new CustomEntity.
     *
     * @return new CustomEntity
     * @throws EntityCreationException when creation fails
     */
    T build() throws EntityCreationException;

}
