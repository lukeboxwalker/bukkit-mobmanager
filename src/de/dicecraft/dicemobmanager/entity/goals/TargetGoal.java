package de.dicecraft.dicemobmanager.entity.goals;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public abstract class TargetGoal<T extends Mob> implements CustomGoal<T> {

    private final T mob;
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
            double d0 = this.getFollowRange();
            if (positionSquared(mob, target) > d0 * d0) {
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

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    private double positionSquared(T mob, LivingEntity target) {
        Location mobLoc = mob.getLocation();
        Location targetLoc = target.getLocation();
        double d0 = mobLoc.getX() - targetLoc.getX();
        double d1 = mobLoc.getY() - targetLoc.getY();
        double d2 = mobLoc.getZ() - targetLoc.getZ();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    private double getFollowRange() {
        AttributeInstance instance = mob.getAttribute(Attribute.GENERIC_FOLLOW_RANGE);
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
