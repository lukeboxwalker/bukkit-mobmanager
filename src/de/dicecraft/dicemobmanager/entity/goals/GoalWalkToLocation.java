package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.Location;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Goal to walk to a location.
 * <p>
 * Only mobs can path find to a location
 * by using {@link Mob#getPathfinder()}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class GoalWalkToLocation implements CustomGoal<Mob> {

    private static final int DEFAULT_SPEED = 1;

    private final Queue<Location> locationQueue;
    private final Mob mob;
    private final float speed;

    // the path to follow
    private Pathfinder.PathResult pathResult;
    // the next goal to walk to
    private Location nextGoal;

    /**
     * Creates a new goal to walk to a location.
     * <p>
     * Uses the default speed to instantiate the goal
     *
     * @param mob  the entity for the goal
     * @param goal the goal location
     */
    public GoalWalkToLocation(final Mob mob, final Location goal) {
        this(mob, goal, DEFAULT_SPEED);
    }

    /**
     * Creates a new goal to walk to a location.
     * <p>
     * Uses the default speed to instantiate the goal
     *
     * @param mob   the entity for the goal
     * @param goals to walk to one by one
     */
    public GoalWalkToLocation(final Mob mob, final List<Location> goals) {
        this(mob, goals, DEFAULT_SPEED);
    }

    /**
     * Creates a new goal to walk to a location.
     * <p>
     *
     * @param mob   the entity for the goal
     * @param speed the speed of which the entity is walking
     * @param goal  the goal location
     */
    public GoalWalkToLocation(final Mob mob, final Location goal, float speed) {
        this(mob, new LinkedList<>(Collections.singletonList(goal)), speed);
    }

    /**
     * Creates a new goal to walk to a location.
     * <p>
     *
     * @param mob   the entity for the goal
     * @param speed the speed of which the entity is walking
     * @param goals to walk to one by one
     */
    public GoalWalkToLocation(final Mob mob, final List<Location> goals, float speed) {
        this.locationQueue = goals == null ? new LinkedList<>() : new LinkedList<>(goals);
        this.mob = mob;
        this.speed = speed;
    }

    /**
     * Checks if this goal should be activated.
     * <p>
     * The goal should be activated if the pathfinder of
     * the mob can find a path to the next location.
     *
     * @return if this goal should be activated
     */
    @Override
    public boolean shouldActivate() {
        if (mob.getTarget() == null) {
            if (!locationQueue.isEmpty() && nextGoal == null) {
                this.nextGoal = locationQueue.poll();
            }
            pathResult = mob.getPathfinder().findPath(nextGoal);
            return pathResult != null;
        } else {
            return false;
        }
    }

    /**
     * Checks if this goal should stay active.
     * <p>
     * The goal should stay active if there are
     * locations left to walk to and there is no target
     * selected from the mob.
     *
     * @return if this goal should stay active
     */
    @Override
    public boolean shouldStayActive() {
        if (pathResult != null && pathResult.getPoints().size() == pathResult.getNextPointIndex() && !locationQueue.isEmpty()) {
            this.nextGoal = null;
            if (this.shouldActivate()) {
                this.start();
            }
        }
        return mob.getTarget() == null && !locationQueue.isEmpty();
    }

    /**
     * Called when this goal gets activated.
     * <p>
     * Starts the movement of the entity along the
     * calculated {@link Pathfinder.PathResult} with
     * given speed
     */
    @Override
    public void start() {
        mob.getPathfinder().moveTo(pathResult, speed);
    }

    /**
     * A unique key that identifies this type of goal.
     *
     * @return the goal key
     */
    @Nonnull
    @Override
    public GoalKey<Mob> getKey() {
        return CustomGoal.WALK_TO_LOCATION;
    }

    /**
     * Specifies the goal types of this goal.
     * <p>
     * Walking to a location is specified by a {@link GoalType#MOVE}
     * as well as a {@link GoalType#JUMP}
     *
     * @return a list of all applicable types for this goal
     */
    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE, GoalType.JUMP);
    }
}
