package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;
import de.dicecraft.dicemobmanager.entity.pathfinder.navigation.CustomPathEntity;
import org.bukkit.Location;

import java.util.Collections;
import java.util.LinkedList;

public class GoalWalkToLocation implements CustomPathfinderGoal {

    private final float speed;
    private final LinkedList<Location> locations;
    private final Location goal;

    private Location currentLocation;
    private CustomPathEntity customPathEntity;

    public GoalWalkToLocation(final Location goal, float speed) {
        this(goal, speed, new LinkedList<>(Collections.singletonList(goal)));
    }

    public GoalWalkToLocation(final Location goal, float speed, final LinkedList<Location> locations) {
        this.goal = goal;
        this.locations = locations;
        this.currentLocation = goal;
        this.speed = speed;
    }

    @Override
    public boolean shouldActivate(final CustomEntityMoving customEntity) {
        if (customEntity.getGoalTarget() == null) {
            if (locations != null && !locations.isEmpty() && checkPos()) {
                this.currentLocation = locations.pollFirst();
            }
            customPathEntity = customEntity.getCustomNavigation().createPathEntity(currentLocation, 0);
            return customPathEntity.hasPath();
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldStayActive(final CustomEntityMoving customEntity) {
        if (customEntity.getCustomNavigation().hasNoPathEntity() && locations != null && !locations.isEmpty()) {
            this.currentLocation = locations.pollFirst();
            if (this.shouldActivate(customEntity)) {
                this.start(customEntity);
            }
        }
        return customEntity.getGoalTarget() == null;
    }

    @Override
    public void start(final CustomEntityMoving customEntity) {
        customEntity.getCustomNavigation().walkPathEntity(customPathEntity, speed);
    }

    @Override
    public void tick(final CustomEntityMoving customEntity) {
    }

    @Override
    public void onTaskReset(final CustomEntityMoving customEntity) {
    }

    private boolean checkPos() {
        return currentLocation.getX() == goal.getX() && currentLocation.getY() == goal.getY() && currentLocation.getZ() == goal.getZ();
    }
}