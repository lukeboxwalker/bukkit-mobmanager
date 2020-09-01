package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import org.bukkit.Location;

import java.util.function.Supplier;

/**
 * Provides all custom pathfinder goals.
 * <p>
 * The pathfinder goals, see {@link CustomPathfinderGoal} or pathfinder goal
 * targets, see {@link CustomPathfinderGoalTarget}, are wrapped by a
 * supplier to capsule the object instantiation and hide the
 * concrete implementation.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public final class CustomGoalProvider {

    /**
     * Provides a walk to location goal.
     * <p>
     * The entity's goal is to walk to the given location
     * with a standard speed of 1. Also see {@link GoalWalkToLocation}
     *
     * @param location the entity should walk to
     * @return supplier of the pathfinder goal
     */
    public static Supplier<CustomPathfinderGoal> WALK_TO_LOCATION(final Location location) {
        return () -> new GoalWalkToLocation(location, 1);
    }

    /**
     * Provides a melee attack goal.
     * <p>
     * The entity's goal is melee attack a specific target
     * with a standard rage of 1.2 and a sensitive behavior.
     * Also see {@link GoalMeleeAttack}
     * <p>
     * The melee attack can only select the goal if a the entity
     * has a target which is set by a custom target goal {@link CustomPathfinderGoalTarget}
     *
     * @return supplier of the pathfinder goal
     */
    public static Supplier<CustomPathfinderGoal> MELEE_ATTACK() {
        return () -> new GoalMeleeAttack(1.2D, true);
    }

    /**
     * Provides a goal target selector.
     * <p>
     * The entity's goal is to select the nearest player as
     * the new target see {@link GoalAttackNearestPlayer} which then
     * can be used by a goal selection {@link CustomPathfinderGoal}
     *
     * @return supplier of the pathfinder goal target
     */
    public static Supplier<CustomPathfinderGoalTarget> ATTACK_NEAREST_PLAYER() {
        return () -> new GoalAttackNearestPlayer(10, true);
    }

    /**
     * Provides a goal target selector.
     * <p>
     * The entity's goal is to select the entity which damage it currently.
     * Also see {@link GoalHurtByTarget} which then sets the correct target
     * that can be used by a goal selection {@link CustomPathfinderGoal}
     *
     * @return supplier of the pathfinder goal target
     */
    public static Supplier<CustomPathfinderGoalTarget> HURT_BY_TARGET() {
        return GoalHurtByTarget::new;
    }

}
