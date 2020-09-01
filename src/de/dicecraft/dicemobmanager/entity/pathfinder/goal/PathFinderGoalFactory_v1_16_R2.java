package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;
import net.minecraft.server.v1_16_R2.PathfinderGoal;

/**
 * Creating nms Pathfinder goals.
 * <p>
 * Used to convert {@link CustomPathfinderGoal} to nms
 * PathfinderGoal to set goal and target selectors in {@link CustomEntityMoving}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public final class PathFinderGoalFactory_v1_16_R2 {

    // entity to create Pathfinders for
    private final CustomEntityMoving customEntityMoving;

    public PathFinderGoalFactory_v1_16_R2(final CustomEntityMoving customEntityMoving) {
        this.customEntityMoving = customEntityMoving;
    }

    /**
     * Creates nms {@link PathfinderGoal}.
     * <p>
     * Connects functions of {@link CustomPathfinderGoal} to
     * original functions used by nms.
     *
     * @param customPathfinderGoal to create Pathfinder
     * @return PathfinderGoal to selecting a goal
     */
    public PathfinderGoal createPathFinderGoal(final CustomPathfinderGoal customPathfinderGoal) {
        return new PathfinderGoal() {

            @Override
            public boolean a() {
                return customPathfinderGoal.shouldActivate(customEntityMoving);
            }

            @Override
            public boolean b() {
                return customPathfinderGoal.shouldStayActive(customEntityMoving);
            }

            @Override
            public void c() {
                customPathfinderGoal.start(customEntityMoving);
            }

            @Override
            public void d() {
                customPathfinderGoal.onTaskReset(customEntityMoving);
            }

            @Override
            public void e() {
                customPathfinderGoal.tick(customEntityMoving);
            }
        };
    }

    /**
     * Creates nms {@link PathfinderGoal}.
     * <p>
     * Connects functions of {@link CustomPathfinderGoalTarget} to
     * original functions used by nms.
     *
     * @param customPathfinderGoalTarget to create Pathfinder
     * @return PathfinderGoal to selecting a target
     */
    public PathfinderGoal createPathFinderGoalTarget(final CustomPathfinderGoalTarget customPathfinderGoalTarget) {
        return new PathfinderGoal() {

            @Override
            public boolean a() {
                return customPathfinderGoalTarget.canSelectTarget(customEntityMoving);
            }

            @Override
            public boolean b() {
                return customPathfinderGoalTarget.shouldStayActive(customEntityMoving);
            }

            @Override
            public void c() {
                customPathfinderGoalTarget.selectTarget(customEntityMoving);
            }

            @Override
            public void d() {
                customPathfinderGoalTarget.onTaskReset(customEntityMoving);
            }

            @Override
            public void e() {
                customPathfinderGoalTarget.tick(customEntityMoving);
            }
        };
    }
}
