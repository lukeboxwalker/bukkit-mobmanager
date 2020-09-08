package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.adapter.EntityCreationException;
import de.dicecraft.dicemobmanager.adapter.CustomEntityFactory;
import de.dicecraft.dicemobmanager.adapter.NMSHandler;
import de.dicecraft.dicemobmanager.entity.CustomEntity;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.datawatcher.CustomDataWatcher;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoal;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoalTarget;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Builds a {@link CustomEntity}.
 *
 * Represents a custom configuration of which
 * an entity can be constructed see {@link EntityConfiguration}.
 *
 * @param <T> specific CustomEntity class
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class EntityBuilder<T extends CustomEntity> implements CustomEntityBuilder<T>, EntityConfiguration<T> {

    private final Map<Attribute, Double> attributes;
    private final List<PriorityEntry<Supplier<CustomPathfinderGoal>>> pathfinderGoals;
    private final List<PriorityEntry<Supplier<CustomPathfinderGoalTarget>>> pathfinderTargets;
    private final Set<CustomDataWatcher> dataWatchers;

    private CustomType<T> customType;
    private World world;

    public EntityBuilder() {
        pathfinderGoals = new ArrayList<>();
        pathfinderTargets = new ArrayList<>();
        dataWatchers = new HashSet<>();
        attributes = new HashMap<>();
    }

    /**
     * Specifies a data watcher the entity should use.
     * <p>
     * The data watcher is needed to provide type specific data
     * to the custom entity.
     * <p>
     * Manipulating {@link EntityBuilder#dataWatchers}
     *
     * @param dataWatcher to install in entity
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder<T> attachDataWatcher(final CustomDataWatcher dataWatcher) {
        dataWatchers.add(dataWatcher);
        return this;
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
    public CustomEntityBuilder<T> setAttribute(final Attribute attribute, final double value) {
        attributes.put(attribute, value);
        return this;
    }

    /**
     * Specifies a pathfinder goal.
     * <p>
     * Using a supplier of {@link CustomPathfinderGoal} to ensure
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
    public CustomEntityBuilder<T> attachGoalSelector(final int priority, Supplier<CustomPathfinderGoal> supplier) {
        pathfinderGoals.add(new PriorityEntry<>(priority, supplier));
        return this;
    }

    /**
     * Specifies a pathfinder goal target.
     * <p>
     * Using a supplier of {@link CustomPathfinderGoalTarget} to ensure
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
    public CustomEntityBuilder<T> attachTargetSelector(final int priority, Supplier<CustomPathfinderGoalTarget> supplier) {
        pathfinderTargets.add(new PriorityEntry<>(priority, supplier));
        return this;
    }

    /**
     * Specifies the type for the entity.
     * <p>
     * The entity type determines the entity model, as
     * well as the specific class to instantiate when building.
     * See also {@link CustomType}
     * <p>
     * Manipulating {@link EntityBuilder#customType}
     *
     * @param customType type of the entity
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder<T> fromType(final CustomType<T> customType) {
        this.customType = customType;
        return this;
    }

    /**
     * Specifies the world for the entity.
     * <p>
     * Manipulating {@link EntityBuilder#world}
     *
     * @param world the world for the entity
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder<T> inWorld(final World world) {
        this.world = world;
        return this;
    }

    /**
     * Builds the {@link CustomEntity}.
     * <p>
     * Using all information given to the builder, it
     * creates a new CustomEntity. Setting default attributes
     * when no custom attribute is set yet.
     * Using {@link CustomEntityFactory} to construct the entity.
     *
     * @return new CustomEntity
     * @throws EntityCreationException when custom type or world is not set yet or
     *                                 the EntityFactory fails to create the custom entity.
     */
    @Override
    public T build() throws EntityCreationException {
        if (customType == null) {
            throw new EntityCreationException("CustomType is not specified");
        } else if (world == null) {
            throw new EntityCreationException("World is not specified");
        }
        attributes.putIfAbsent(Attribute.MAX_HEALTH, 20D);
        attributes.putIfAbsent(Attribute.KNOCKBACK_RESISTANCE, -1D);
        attributes.putIfAbsent(Attribute.ARMOR_TOUGHNESS, -1D);
        attributes.putIfAbsent(Attribute.FOLLOW_RANGE, 15D);
        attributes.putIfAbsent(Attribute.ATTACK_KNOCKBACK, -1D);
        attributes.putIfAbsent(Attribute.MOVEMENT_SPEED, 0.23D);
        attributes.putIfAbsent(Attribute.ATTACK_DAMAGE, 3.0D);
        attributes.putIfAbsent(Attribute.ARMOR, 0D);
        attributes.putIfAbsent(Attribute.SPAWN_REINFORCEMENTS, -1D);
        return NMSHandler.getEntityFactory().createCustomEntity(this);
    }

    /**
     * Gets the {@link CustomType}.
     * <p>
     * The type is needed to specify the entity
     * model as well as the class for constriction
     * <p>
     * The world {@link EntityBuilder#customType} is manipulated
     * by {@link EntityBuilder#fromType(CustomType)}
     *
     * @return custom type for an entity
     */
    @Override
    public CustomType<T> getEntityType() {
        return customType;
    }

    /**
     * Gets the {@link World}.
     * <p>
     * The bukkit world is needed to create the entity.
     * <p>
     * The world {@link EntityBuilder#world} is manipulated
     * by {@link EntityBuilder#inWorld(World)}
     *
     * @return custom type for an entity
     */
    @Override
    public World getWorld() {
        return world;
    }

    /**
     * Gets all pathfinder goals.
     * <p>
     * Pathfinder goals will select entities next
     * goal according to their priority. Also see {@link CustomPathfinderGoal}
     * <p>
     * The list {@link EntityBuilder#pathfinderGoals} is manipulated
     * by {@link EntityBuilder#attachGoalSelector(int, Supplier)}
     *
     * @return list of pathfinder goal suppliers pared with there
     * according priorities
     */
    @Override
    public List<PriorityEntry<Supplier<CustomPathfinderGoal>>> getPathfinderGoals() {
        return pathfinderGoals;
    }

    /**
     * Gets all pathfinder goal targets.
     * <p>
     * Pathfinder goal targets will select entities next
     * target according to their priority. Also see {@link CustomPathfinderGoalTarget}
     * <p>
     * The list {@link EntityBuilder#pathfinderTargets} is manipulated
     * by {@link EntityBuilder#attachTargetSelector(int, Supplier)}
     *
     * @return list of pathfinder goal target suppliers pared with there
     * according priorities
     */
    @Override
    public List<PriorityEntry<Supplier<CustomPathfinderGoalTarget>>> getPathfinderTargets() {
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

    /**
     * Gets all data watchers.
     * <p>
     * The data watchers need to be installed to
     * provide type specific data see {@link CustomDataWatcher}
     * <p>
     * The set {@link EntityBuilder#dataWatchers} is manipulated
     * by {@link EntityBuilder#attachDataWatcher(CustomDataWatcher)}
     *
     * @return set of data watchers
     */
    @Override
    public Set<CustomDataWatcher> getDataWatchers() {
        return dataWatchers;
    }
}
