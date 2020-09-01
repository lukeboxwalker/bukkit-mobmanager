package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;

public interface CustomPathfinderGoal {

    boolean shouldActivate(final CustomEntityMoving customEntity);

    boolean shouldStayActive(final CustomEntityMoving customEntity);

    void start(final CustomEntityMoving customEntity);

    void tick(final CustomEntityMoving customEntity);

    void onTaskReset(final CustomEntityMoving customEntity);
}
