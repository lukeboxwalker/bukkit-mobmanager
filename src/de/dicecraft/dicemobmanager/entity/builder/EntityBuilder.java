package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import de.dicecraft.dicemobmanager.entity.factory.EntityFactory;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


/**
 * Builds a Entity.
 *
 * Represents a custom configuration of which
 * an entity can be constructed see {@link EntityConfiguration}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class EntityBuilder implements CustomEntityBuilder, EntityConfiguration {

    private final Plugin plugin;
    private final Map<Attribute, Double> attributes;
    private final List<PriorityEntry<Function<Mob, Goal<Mob>>>> pathfinderGoals;
    private final List<PriorityEntry<Function<Mob, Goal<Mob>>>> pathfinderTargets;

    private EntityType entityType;
    private Location location;

    public EntityBuilder(final Plugin plugin) {
        this.plugin = plugin;
        this.pathfinderGoals = new ArrayList<>();
        this.pathfinderTargets = new ArrayList<>();
        this.attributes = new HashMap<>();
    }

    /**
     * Specifies an attribute for the entity.
     * <p>
     * All attributes, see {@link Attribute} are collected and take on entity
     * when building. Value should be positive to set custom attribute value.
     * If the value is negative the attribute is considered to use the default
     * value.
     * <p>
     * Manipulating {@link EntityBuilder#attributes}
     *
     * @param attribute attribute type to set value for
     * @param value     the value for the given attribute
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder setAttribute(final Attribute attribute, final double value) {
        attributes.put(attribute, value);
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
     * <p>
     * Manipulating {@link EntityBuilder#pathfinderGoals}
     *
     * @param priority the priority of the goal should be non negative
     * @param supplier to supply pathfinder goals
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder attachGoalSelector(final int priority, Function<Mob, Goal<Mob>> supplier) {
        pathfinderGoals.add(new PriorityEntry<>(priority, supplier));
        return this;
    }

    /**
     * Specifies a pathfinder goal target.
     * <p>
     * Using a supplier of {@link Goal} to ensure
     * to provide a unique object for each entity when building.
     * Each goal target has a priority to determine the order to use them.
     * The target selection always prefers lower prioritised pathfinder goal targets.
     * The highest priority that is possible is 1.
     * <p>
     * Manipulating {@link EntityBuilder#pathfinderTargets}
     *
     * @param priority the priority of the goal target should be non negative
     * @param supplier to supply pathfinder goal targets
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder attachTargetSelector(final int priority, Function<Mob, Goal<Mob>> supplier) {
        pathfinderTargets.add(new PriorityEntry<>(priority, supplier));
        return this;
    }

    /**
     * Specifies the type for the entity.
     * <p>
     * The entity type determines the entity model, as
     * well as the specific class to instantiate when building.
     * See also {@link EntityType}
     * <p>
     * Manipulating {@link EntityBuilder#entityType}
     *
     * @param entityType type of the entity
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder fromType(final EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    /**
     * Specifies the location for the entity.
     * <p>
     * Manipulating {@link EntityBuilder#location}
     *
     * @param location the world for the entity
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder atLocation(final Location location) {
        this.location = location;
        return this;
    }

    /**
     * Builds the Entity.
     * <p>
     * Using all information given to the builder, it
     * creates a new CustomEntity. Setting default attributes
     * when no custom attribute is set yet.
     * Using {@link EntityFactory} to construct the entity.
     *
     * @return new CustomEntity
     * @throws EntityCreationException when custom type or world is not set yet or
     *                                 the EntityFactory fails to create the custom entity.
     */
    @Override
    public Entity build() throws EntityCreationException {
        if (entityType == null) {
            throw new EntityCreationException("CustomType is not specified");
        } else if (location == null) {
            throw new EntityCreationException("World is not specified");
        }
        attributes.putIfAbsent(Attribute.GENERIC_MAX_HEALTH, 20D);
        attributes.putIfAbsent(Attribute.GENERIC_KNOCKBACK_RESISTANCE, -1D);
        attributes.putIfAbsent(Attribute.GENERIC_ARMOR_TOUGHNESS, -1D);
        attributes.putIfAbsent(Attribute.GENERIC_FOLLOW_RANGE, 15D);
        attributes.putIfAbsent(Attribute.GENERIC_ATTACK_KNOCKBACK, -1D);
        attributes.putIfAbsent(Attribute.GENERIC_MOVEMENT_SPEED, 0.23D);
        attributes.putIfAbsent(Attribute.GENERIC_ATTACK_DAMAGE, 3.0D);
        attributes.putIfAbsent(Attribute.GENERIC_ARMOR, 0D);
        return new EntityFactory().spawnEntity(this, plugin);
    }

    /**
     * Gets the EntityType.
     * <p>
     * The type is needed to specify the entity
     * model as well as the class for constriction
     * <p>
     * The world {@link EntityBuilder#entityType} is manipulated
     * by {@link EntityBuilder#fromType(EntityType)}
     *
     * @return custom type for an entity
     */
    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Gets the Location.
     * <p>
     * The bukkit world is needed to create the entity.
     * <p>
     * The world {@link EntityBuilder#location} is manipulated
     * by {@link EntityBuilder#atLocation(Location)}
     *
     * @return custom type for an entity
     */
    @Override
    public Location getLocation() {
        return location;
    }

    /**
     * Gets all pathfinder goals.
     * <p>
     * Pathfinder goals will select entities next
     * goal according to their priority. Also see {@link Goal}
     * <p>
     * The list {@link EntityBuilder#pathfinderGoals} is manipulated
     * by {@link EntityBuilder#attachGoalSelector(int, Function)}
     *
     * @return list of pathfinder goal suppliers pared with there
     * according priorities
     */
    @Override
    public List<PriorityEntry<Function<Mob, Goal<Mob>>>> getPathfinderGoals() {
        return pathfinderGoals;
    }

    /**
     * Gets all pathfinder goal targets.
     * <p>
     * Pathfinder goal targets will select entities next
     * target according to their priority. Also see {@link Goal}
     * <p>
     * The list {@link EntityBuilder#pathfinderTargets} is manipulated
     * by {@link EntityBuilder#attachTargetSelector(int, Function)}
     *
     * @return list of pathfinder goal target suppliers pared with there
     * according priorities
     */
    @Override
    public List<PriorityEntry<Function<Mob, Goal<Mob>>>> getPathfinderTargets() {
        return pathfinderTargets;
    }

    /**
     * Gets the attribute map.
     * <p>
     * Attributes are pared with there according value.
     * The map contains all attributes, also see {@link Attribute},
     * with valid default values if no custom value is set.
     * <p>
     * If the value is negative the entity should use the
     * nms default value.
     * <p>
     * The map {@link EntityBuilder#attributes} is manipulated
     * by {@link EntityBuilder#setAttribute(Attribute, double)}
     *
     * @return map of attributes and their values
     */
    @Override
    public Map<Attribute, Double> getAttributes() {
        return attributes;
    }
}
