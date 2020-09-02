package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.CustomEntity;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.datawatcher.CustomDataObject;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoal;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoalTarget;
import org.bukkit.World;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Configuration of a {@link CustomEntity}.
 * <p>
 * Represents a custom configuration of which
 * an entity can be constructed.
 *
 * @param <T> specific CustomEntity class
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface EntityConfiguration<T extends CustomEntity> {

    /**
     * Gets all data watchers.
     * <p>
     * The data watchers need to be installed to
     * provide type specific data see {@link CustomDataObject}
     *
     * @return set of data watchers
     */
    Set<CustomDataObject> getDataWatchers();

    /**
     * Gets the {@link CustomType}.
     * <p>
     * The type is needed to specify the entity
     * model as well as the class for constriction
     *
     * @return custom type for an entity
     */
    CustomType<T> getEntityType();

    /**
     * Gets the {@link World}.
     * <p>
     * The bukkit world is needed to create
     * the entity.
     *
     * @return custom type for an entity
     */
    World getWorld();

    /**
     * Gets all pathfinder goals.
     * <p>
     * Pathfinder goals will select entities next
     * goal according to their priority. Also see {@link CustomPathfinderGoal}
     *
     * @return list of pathfinder goal suppliers pared with there
     * according priorities
     */
    List<PriorityEntry<Supplier<CustomPathfinderGoal>>> getPathfinderGoals();

    /**
     * Gets all pathfinder goal targets.
     * <p>
     * Pathfinder goal targets will select entities next
     * target according to their priority. Also see {@link CustomPathfinderGoalTarget}
     *
     * @return list of pathfinder goal target suppliers pared with there
     * according priorities
     */
    List<PriorityEntry<Supplier<CustomPathfinderGoalTarget>>> getPathfinderTargets();

    /**
     * Gets the attribute map.
     * <p>
     * Attributes are pared with there according value.
     * The map contains all attributes, also see {@link Attribute},
     * with valid default values if no custom value is set.
     * <p>
     * If the value is negative the entity should use the
     * nms default value.
     *
     * @return map of attributes and their values
     */
    Map<Attribute, Double> getAttributes();
}
