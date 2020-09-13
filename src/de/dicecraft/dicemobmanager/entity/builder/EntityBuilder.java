package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.MobGoals;
import com.destroystokyo.paper.entity.ai.PaperMobGoals;
import de.dicecraft.dicemobmanager.entity.CustomEntities;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;


/**
 * Builds a Entity.
 *
 * Represents a custom configuration of which
 * an entity can be constructed.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class EntityBuilder implements CustomEntityBuilder {

    private final Plugin plugin;
    private final Map<Attribute, Double> attributes;
    private final List<PriorityEntry<Function<Mob, Goal<Mob>>>> pathfinderGoals;
    private final List<PriorityEntry<Function<Mob, Goal<Mob>>>> pathfinderTargets;
    private final MobGoals mobGoals;

    private EntityType entityType;
    private Location location;
    private EntityInformation information;

    public EntityBuilder(@Nonnull final Plugin plugin) {
        mobGoals = new PaperMobGoals();
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
    public CustomEntityBuilder setAttribute(@Nonnull final Attribute attribute, final double value) {
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
    public CustomEntityBuilder attachGoalSelector(final int priority, @Nonnull Function<Mob, Goal<Mob>> supplier) {
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
    public CustomEntityBuilder attachTargetSelector(final int priority, @Nonnull Function<Mob, Goal<Mob>> supplier) {
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
    public CustomEntityBuilder fromType(@Nonnull final EntityType entityType) {
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
    public CustomEntityBuilder atLocation(@Nonnull final Location location) {
        this.location = location;
        return this;
    }

    /**
     * Specifies the custom information for the entity.
     * <p>
     * Manipulating {@link EntityBuilder#information}
     *
     * @param information the world for the entity
     * @return builder to continue
     */
    @Override
    public CustomEntityBuilder useInformation(@Nonnull final EntityInformation information) {
        this.information = information;
        return this;
    }

    /**
     * Builds the Entity.
     * <p>
     * Using all information given to the builder, it
     * creates a new Entity. Setting default attributes
     * when no custom attribute is set yet.
     *
     * @return new CustomEntity
     * @throws EntityCreationException when custom type or world is not set yet or
     *                                 the EntityFactory fails to create the custom entity.
     */
    @Override
    public Entity buildAndSpawn() throws EntityCreationException {
        if (entityType == null) throw new EntityCreationException("Type is not specified");
        if (location == null) throw new EntityCreationException("Location is not specified");
        if (information == null) throw new EntityCreationException("Entity information is not specified");

        attributes.putIfAbsent(Attribute.GENERIC_MAX_HEALTH, 20D);
        attributes.putIfAbsent(Attribute.GENERIC_KNOCKBACK_RESISTANCE, -1D);
        attributes.putIfAbsent(Attribute.GENERIC_ARMOR_TOUGHNESS, -1D);
        attributes.putIfAbsent(Attribute.GENERIC_FOLLOW_RANGE, 15D);
        attributes.putIfAbsent(Attribute.GENERIC_ATTACK_KNOCKBACK, -1D);
        attributes.putIfAbsent(Attribute.GENERIC_MOVEMENT_SPEED, 0.23D);
        attributes.putIfAbsent(Attribute.GENERIC_ATTACK_DAMAGE, 3.0D);
        attributes.putIfAbsent(Attribute.GENERIC_ARMOR, 0D);

        if (Mob.class.isAssignableFrom(Objects.requireNonNull(entityType.getEntityClass()))) {
            return location.getWorld().spawnEntity(location, entityType, CreatureSpawnEvent.SpawnReason.CUSTOM, entity -> {
                Mob mob = (Mob) entity;
                attributes.forEach((attribute, value) -> {
                    AttributeInstance instance = mob.getAttribute(attribute);
                    if (instance != null) {
                        instance.setBaseValue(value);
                    }
                });
                mob.setHealth(attributes.get(Attribute.GENERIC_MAX_HEALTH));
                entity.setCustomNameVisible(true);
                for (PriorityEntry<Function<Mob, Goal<Mob>>> entry : pathfinderGoals) {
                    mobGoals.addGoal((Mob) entity, entry.getPriority(), entry.getEntry().apply((Mob) entity));
                }
                if (entity instanceof Zombie) {
                    ((Zombie) entity).setShouldBurnInDay(false);
                }
                CustomEntities.addEntity(mob, information, plugin);
            });
        } else {
            throw new EntityCreationException("Entity type is not a mob");
        }
    }
}
