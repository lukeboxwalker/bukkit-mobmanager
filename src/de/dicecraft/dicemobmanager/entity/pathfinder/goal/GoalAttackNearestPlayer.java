package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntity;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.EntityPlayer;
import net.minecraft.server.v1_16_R2.PathfinderTargetCondition;

import org.bukkit.event.entity.EntityTargetEvent;

import java.util.function.Predicate;

public class GoalAttackNearestPlayer extends CustomGoalTarget {
    protected final int random;
    protected EntityLiving target;
    protected PathfinderTargetCondition targetCondition;

    public GoalAttackNearestPlayer(int random, boolean flag) {
        super(flag);
        this.random = random;
    }

    public boolean canSelectTarget(final CustomEntityMoving customEntity) {
        if (this.random > 0 && customEntity.getRandom().nextInt(this.random) != 0) {
            return false;
        } else {
            final double followRange = this.getFollowRange(customEntity);
            if (targetCondition == null) {
                final Predicate<EntityLiving> predicate = (entityLiving) -> !(entityLiving instanceof CustomEntity);
                this.targetCondition = new PathfinderTargetCondition().a(followRange).a(predicate);
                if (customEntity.world.paperConfig.entitiesTargetWithFollowRange) {
                    this.targetCondition.useFollowRange();
                }
            }
            this.target = customEntity.world.b(EntityPlayer.class, this.targetCondition, customEntity,
                    customEntity.locX(), customEntity.getHeadY(), customEntity.locZ(),
                    customEntity.getBoundingBox().grow(followRange, 4.0D, followRange));
            return this.target != null;
        }
    }

    public void selectTarget(final CustomEntityMoving customEntity) {
        EntityTargetEvent.TargetReason targetReason;
        if (this.target instanceof EntityPlayer) {
            targetReason = EntityTargetEvent.TargetReason.CLOSEST_PLAYER;
        } else {
            targetReason = EntityTargetEvent.TargetReason.CLOSEST_ENTITY;
        }
        customEntity.setGoalTarget(this.target, targetReason, true);
        super.selectTarget(customEntity);
    }
}