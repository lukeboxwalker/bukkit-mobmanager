package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;
import net.minecraft.server.v1_16_R2.EntityHuman;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.GenericAttributes;
import net.minecraft.server.v1_16_R2.ScoreboardTeamBase;

import org.bukkit.event.entity.EntityTargetEvent;

public abstract class CustomGoalTarget implements CustomPathfinderGoalTarget {
    protected final boolean f;
    private int d;
    protected EntityLiving target;
    protected int h;

    public CustomGoalTarget(boolean flag) {
        this.h = 60;
        this.f = flag;
    }

    @Override
    public boolean shouldStayActive(final CustomEntityMoving customEntity) {
        EntityLiving target = customEntity.getGoalTarget();
        if (target == null) {
            target = this.target;
        }

        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else {
            ScoreboardTeamBase entityTeam = customEntity.getScoreboardTeam();
            ScoreboardTeamBase targetTeam = target.getScoreboardTeam();
            if (entityTeam != null && targetTeam == entityTeam) {
                return false;
            } else {
                double followRange = customEntity.b(GenericAttributes.FOLLOW_RANGE);
                if (customEntity.h(target) > followRange * followRange) {
                    return false;
                } else {
                    if (this.f) {
                        if (customEntity.getEntitySenses().a(target)) {
                            this.d = 0;
                        } else if (++this.d > this.h) {
                            return false;
                        }
                    }

                    if (target instanceof EntityHuman && ((EntityHuman) target).abilities.isInvulnerable) {
                        return false;
                    } else {
                        customEntity.setGoalTarget(target, EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
                        return true;
                    }
                }
            }
        }
    }

    public double getFollowRange(final CustomEntityMoving customEntity) {
        return customEntity.b(GenericAttributes.FOLLOW_RANGE);
    }

    @Override
    public void tick(final CustomEntityMoving customEntity) {
    }

    @Override
    public void selectTarget(final CustomEntityMoving customEntity) {
        this.d = 0;
    }

    @Override
    public void onTaskReset(final CustomEntityMoving customEntity) {
        customEntity.setGoalTarget(null, EntityTargetEvent.TargetReason.FORGOT_TARGET, true);
        this.target = null;
    }
}