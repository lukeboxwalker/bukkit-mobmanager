package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;

public interface CustomPathfinderGoalTarget {

    boolean canSelectTarget(final CustomEntityMoving customEntity);

    boolean shouldStayActive(final CustomEntityMoving customEntity);

    void selectTarget(final CustomEntityMoving customEntity);

    void tick(final CustomEntityMoving customEntity);

    void onTaskReset(final CustomEntityMoving customEntity);
}
