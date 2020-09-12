package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Configuration of an Entity.
 * <p>
 * Represents a custom configuration of which
 * an entity can be constructed.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface EntityConfiguration {

    /**
     * Gets the EntityType.
     * <p>
     * The type is needed to specify the entity
     * model as well as the class for constriction
     *
     * @return custom type for an entity
     */
    EntityType getEntityType();

    /**
     * Gets the location.
     * <p>
     * The bukkit location is needed to create
     * the entity.
     *
     * @return custom type for an entity
     */
    Location getLocation();

    /**
     * Gets all pathfinder goals.
     * <p>
     * Pathfinder goals will select entities next
     * goal according to their priority. Also see {@link Goal}
     *
     * @return list of pathfinder goal suppliers pared with there
     * according priorities
     */
    List<PriorityEntry<Function<Mob, Goal<Mob>>>> getPathfinderGoals();

    /**
     * Gets all pathfinder goal targets.
     * <p>
     * Pathfinder goal targets will select entities next
     * target according to their priority. Also see {@link Goal}
     *
     * @return list of pathfinder goal target suppliers pared with there
     * according priorities
     */
    List<PriorityEntry<Function<Mob, Goal<Mob>>>> getPathfinderTargets();

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
