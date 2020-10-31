package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.utils.PositionUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;


public class GoalNearestTarget<T extends LivingEntity> extends TargetGoal<Mob> {

    private final Class<T> targetClass;
    private final double radius;
    private T target;

    /**
     * Creates a new GoalNearestTarget.
     * <p>
     * Selecting a target around the mob that uses this goal.
     *
     * @param mob         the mob that uses the goal.
     * @param targetClass the entity class types the goal will target.
     * @param radius      the radius in which the goal will search for a target.
     */
    public GoalNearestTarget(final Mob mob, final Class<T> targetClass, final double radius) {
        super(mob);
        this.targetClass = targetClass;
        this.radius = radius;
        this.target = null;
    }

    @Override
    public boolean shouldActivate() {
        if (DiceMobManager.getRandom().nextInt(1) == 0) {
            final Collection<T> entities = mob.getWorld()
                    .getNearbyEntitiesByType(targetClass, mob.getLocation(), radius);
            double minDistance = radius;
            for (final T entity : entities) {
                final double distance = PositionUtils.distanceSquared(mob, entity);
                if (distance < minDistance) {
                    minDistance = distance;
                    target = entity;
                }
            }
            setTarget(target);
            return target != null;
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        mob.setTarget(target);
        super.start();
    }

    @Nonnull
    @Override
    public GoalKey<Mob> getKey() {
        return NEAREST_TARGET;
    }

    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET);
    }
}
