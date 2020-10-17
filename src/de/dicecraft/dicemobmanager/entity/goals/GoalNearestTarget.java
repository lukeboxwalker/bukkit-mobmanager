package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;


public class GoalNearestTarget<T extends LivingEntity> extends TargetGoal<Mob> {

    private final Class<T> targetClass;
    private final double radius;
    private T target;

    public GoalNearestTarget(Mob mob, Class<T> targetClass, double radius) {
        super(mob);
        this.targetClass = targetClass;
        this.radius = radius;
        this.target = null;
    }

    @Override
    public boolean shouldActivate() {
        if (DiceMobManager.getRandom().nextInt(1) != 0) {
            return false;
        } else {
            Collection<T> entities = mob.getWorld().getNearbyEntitiesByType(targetClass, mob.getLocation(), radius);
            double minDistance = radius;
            for (T entity : entities) {
                double distance = positionSquared(mob, entity);
                if (distance < minDistance) {
                    minDistance = distance;
                    target = entity;
                }
            }
            setTarget(target);
            return target != null;
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
