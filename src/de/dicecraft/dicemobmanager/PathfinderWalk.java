package de.dicecraft.dicemobmanager;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;


import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedList;

public class PathfinderWalk implements Goal<Mob> {

    private final Mob mob;
    private final LinkedList<Location> locations;
    private final Location goal;
    private final float speed;
    private final GoalKey<Mob> goalKey;

    private Pathfinder.PathResult pathResult;
    private Location currentLocation;

    public PathfinderWalk(final Mob mob, final Location goal) {
        this(mob, goal, 1, new LinkedList<>(Collections.singletonList(goal)));
    }

    public PathfinderWalk(final Mob mob, final Location goal, float speed) {
        this(mob, goal, speed, new LinkedList<>(Collections.singletonList(goal)));
    }

    public PathfinderWalk(final Mob mob, final Location goal, float speed, final LinkedList<Location> locations) {
        this.mob = mob;
        this.goal = goal;
        this.locations = locations;
        this.currentLocation = goal;
        this.speed = speed;
        this.goalKey = GoalKey.of(Mob.class, new NamespacedKey(DiceMobManager.getInstance(), "walktolocation"));
    }


    @Override
    public boolean shouldActivate() {
        if (mob.getTarget() == null) {
            if (locations != null && !locations.isEmpty() && checkPos()) {
                this.currentLocation = locations.pollFirst();
            }
            pathResult = mob.getPathfinder().findPath(currentLocation);
            return pathResult != null;
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        mob.getPathfinder().moveTo(pathResult, speed);
    }

    @Nonnull
    @Override
    public GoalKey<Mob> getKey() {
        return goalKey;
    }

    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }

    private boolean checkPos() {
        return currentLocation.getX() == goal.getX() && currentLocation.getY() == goal.getY() && currentLocation.getZ() == goal.getZ();
    }
}
