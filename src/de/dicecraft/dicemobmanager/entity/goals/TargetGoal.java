package de.dicecraft.dicemobmanager.entity.goals;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public abstract class TargetGoal<T extends Mob> implements CustomGoal<T> {

    protected final T mob;
    private LivingEntity target;

    public TargetGoal(T mob) {
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
            if (positionSquared(mob, target) > followRange * followRange) {
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

    protected double positionSquared(final T mob, final LivingEntity target) {
        final Location mobLoc = mob.getLocation();
        final Location targetLoc = target.getLocation();
        final double x = mobLoc.getX() - targetLoc.getX();
        final double y = mobLoc.getY() - targetLoc.getY();
        final double z = mobLoc.getZ() - targetLoc.getZ();
        return x * x + y * y + z * z;
    }

    private double getFollowRange() {
        final AttributeInstance instance = mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE);
        if (instance != null) {
            return instance.getValue();
        } else {
            return 0;
        }
    }

    @Override
    public void stop() {
        this.mob.setTarget(null);
        this.target = null;
    }
}
