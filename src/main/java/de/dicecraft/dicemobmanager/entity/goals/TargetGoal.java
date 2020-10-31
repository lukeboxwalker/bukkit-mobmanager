package de.dicecraft.dicemobmanager.entity.goals;

import de.dicecraft.dicemobmanager.utils.PositionUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public abstract class TargetGoal<T extends Mob> implements CustomGoal<T> {

    protected final T mob;
    private LivingEntity target;

    public TargetGoal(final T mob) {
        this.mob = mob;
    }

    @Override
    public boolean shouldStayActive() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            target = this.target;
        }

        if (target == null) {
            return false;
        } else if (target.isDead()) {
            return false;
        } else {
            final double followRange = this.getFollowRange();
            if (PositionUtils.distanceSquared(mob, target) > followRange * followRange) {
                return false;
            } else {
                if (target.isInvulnerable()) {
                    return false;
                } else {
                    mob.setTarget(target);
                    return true;
                }
            }

        }
    }

    public void setTarget(final LivingEntity target) {
        this.target = target;
    }

    private double getFollowRange() {
        final AttributeInstance instance = mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE);
        if (instance == null) {
            return 0;
        } else {
            return instance.getValue();
        }
    }

    @Override
    public void stop() {
        this.mob.setTarget(null);
        this.target = null;
    }
}
