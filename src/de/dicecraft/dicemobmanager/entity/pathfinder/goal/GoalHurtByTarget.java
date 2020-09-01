package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.GameRules;
import net.minecraft.server.v1_16_R2.PathfinderTargetCondition;
import org.bukkit.event.entity.EntityTargetEvent;

public class GoalHurtByTarget extends CustomGoalTarget {
    private static final PathfinderTargetCondition condition = (new PathfinderTargetCondition()).c().e();
    private int hurtTimestamp;

    public GoalHurtByTarget() {
        super(true);
    }

    public boolean canSelectTarget(final CustomEntityMoving entityMoving) {
        int hurtTimestamp = entityMoving.cZ();
        EntityLiving entityliving = entityMoving.getLastDamager();
        if (hurtTimestamp != this.hurtTimestamp && entityliving != null) {
            if (entityliving.getEntityType() == EntityTypes.PLAYER && entityMoving.getWorld().getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                return false;
            } else {
                return this.checkLastDamageSource(entityMoving, entityliving, condition);
            }
        } else {
            return false;
        }
    }

    public void selectTarget(final CustomEntityMoving entityMoving) {
        entityMoving.setGoalTarget(entityMoving.getLastDamager(), EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY, true);
        this.target = entityMoving.getGoalTarget();
        this.hurtTimestamp = entityMoving.cZ();
        this.h = 300;
        super.selectTarget(entityMoving);
    }
}
