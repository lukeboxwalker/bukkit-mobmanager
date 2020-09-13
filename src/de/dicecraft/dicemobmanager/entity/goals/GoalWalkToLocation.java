package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.Queue;

public class GoalWalkToLocation implements Goal<Mob> {

    private static final int DEFAULT_SPEED = 1;
    private static final GoalKey<Mob> GOAL_KEY = GoalKey.of(Mob.class, new NamespacedKey(DiceMobManager.getInstance(), "walktolocation"));

    private final Mob mob;
    private final Queue<Location> locationQueue;
    private final float speed;

    private Pathfinder.PathResult pathResult;
    private Location nextGoal;

    public GoalWalkToLocation(final Mob mob, final Location goal) {
        this(mob, goal, DEFAULT_SPEED);
    }

    public GoalWalkToLocation(final Mob mob, final Queue<Location> goals) {
        this(mob, goals, DEFAULT_SPEED);
    }

    public GoalWalkToLocation(final Mob mob, final Location goal, float speed) {
        this(mob, new LinkedList<>(Collections.singletonList(goal)), speed);
    }

    public GoalWalkToLocation(final Mob mob, final Queue<Location> locationQueue, float speed) {
        this.locationQueue = locationQueue == null ? new LinkedList<>() : locationQueue;
        this.mob = mob;
        this.speed = speed;
    }


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


    @Override
    public void start() {
        mob.getPathfinder().moveTo(pathResult, speed);
    }

    @Nonnull
    @Override
    public GoalKey<Mob> getKey() {
        return GOAL_KEY;
    }

    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}
