package de.dicecraft.dicemobmanager.entity.pathfinder.goal;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;
import de.dicecraft.dicemobmanager.entity.pathfinder.navigation.CustomPathEntity;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.MainHand;

import java.util.Objects;

public class GoalMeleeAttack implements CustomPathfinderGoal {

    private final double range;
    private final boolean sensitive;

    private CustomPathEntity customPathEntity;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int random;
    private long timeWhenLastHit;
    private int i;

    public GoalMeleeAttack(final double range, final boolean sensitive) {
        this.range = range;
        this.sensitive = sensitive;
    }

    public boolean shouldActivate(final CustomEntityMoving customEntity) {
        long currentTime = customEntity.world.getTime();
        if (currentTime - timeWhenLastHit < 20L) {
            return false;
        } else {
            final LivingEntity target = customEntity.getTargetGoal();
            this.timeWhenLastHit = currentTime;
            if (target == null) {
                return false;
            } else if (target.isDead()) {
                return false;
            } else {

                customPathEntity = customEntity.getCustomNavigation().createPathEntity(customEntity.getTargetGoal(), 0);
                if (customPathEntity != null) {
                    return true;
                } else {
                    Location targetLocation = target.getLocation();
                    return checkTarget(customEntity, target) >= customEntity.h(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ());
                }
            }
        }
    }

    public boolean shouldStayActive(final CustomEntityMoving customEntity) {
        final LivingEntity target = customEntity.getTargetGoal();
        if (target == null) {
            return false;
        } else if (target.isDead()) {
            return false;
        } else if (!this.sensitive) {
            return customEntity.getCustomNavigation().hasNoPathEntity();
        } else if (!customEntity.checkLocation(target.getLocation())) {
            return false;
        } else {
            if (target instanceof HumanEntity) {
                GameMode gameMode = ((HumanEntity)target).getGameMode();
                return !(GameMode.SPECTATOR.equals(gameMode) || GameMode.CREATIVE.equals(gameMode));
            } else {
                return true;
            }
        }
    }

    public void start(final CustomEntityMoving customEntity) {
        customEntity.getCustomNavigation().walkPathEntity(customPathEntity, range);
        customEntity.setAggressive(true);
        this.random = 0;
        this.i = 0;
    }

    public void onTaskReset(final CustomEntityMoving customEntity) {
        final LivingEntity target = customEntity.getTargetGoal();
        if (target instanceof HumanEntity) {
            GameMode gameMode = ((HumanEntity)target).getGameMode();
            if (GameMode.SPECTATOR.equals(gameMode) || GameMode.CREATIVE.equals(gameMode)) {
                customEntity.setGoalTarget(null);
            }
        }
        customEntity.setAggressive(false);
        customEntity.getCustomNavigation().resetPathEntity();
    }

    public void tick(final CustomEntityMoving customEntity) {
        final LivingEntity target = customEntity.getTargetGoal();
        assert target != null;
        final Location targetLocation = target.getLocation();
        customEntity.getControllerLook().a(customEntity.getGoalTarget(), 30.0F, 30.0F);
        double factor = customEntity.h(targetLocation.getX(), targetLocation.getY(), targetLocation.getZ());
        random = Math.max(random - 1, 0);
        if ((sensitive || customEntity.getEntitySenses().a(customEntity.getGoalTarget())) &&
                random <= 0 && (targetX == 0.0D && targetY == 0.0D && targetZ == 0.0D
                || Objects.requireNonNull(customEntity.getGoalTarget()).h(targetX, targetY, targetZ) >= 1.0D
                || customEntity.getRandom().nextFloat() < 0.05F)) {
            targetX = targetLocation.getX();
            targetY = targetLocation.getY();
            targetZ = targetLocation.getZ();
            random = 4 + customEntity.getRandom().nextInt(7);
            if (factor > 1024.0D) {
                random += 10;
            } else if (factor > 256.0D) {
                random += 5;
            }
            if (!customEntity.getCustomNavigation().checkPath(target, range)) {
                random += 15;
            }
        }

        this.i = Math.max(this.i - 1, 0);
        this.attack(customEntity, factor);
    }

    private void attack(CustomEntityMoving customEntity, double factor) {
        assert customEntity.getTargetGoal() != null;
        double range = checkTarget(customEntity, customEntity.getTargetGoal());
        if (factor <= range && this.i <= 0) {
            this.i = 20;
            customEntity.swingHand(MainHand.RIGHT);
            customEntity.attackEntity(customEntity.getGoalTarget());
        }
    }

    private double checkTarget(CustomEntityMoving customEntity, LivingEntity target) {
        return (customEntity.getWidth() * 2.0F * customEntity.getWidth() * 2.0F + target.getWidth());
    }
}