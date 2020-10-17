package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import java.util.List;

@SuppressWarnings("checkstyle:InterfaceIsType")
public interface CustomGoal<T extends Mob> extends Goal<T> {
    GoalKey<Mob> WALK_TO_LOCATION = GoalKey.of(Mob.class, DiceMobManager.createNameSpacedKey("walk_to_location"));
    GoalKey<Mob> AVOID_TARGET = GoalKey.of(Mob.class, DiceMobManager.createNameSpacedKey("avoid_target"));
    GoalKey<Mob> HURT_BY_TARGET = GoalKey.of(Mob.class, DiceMobManager.createNameSpacedKey("hurt_by_target"));

    static GoalSupplier<Mob> hurtByTarget(final Class<? extends LivingEntity> attackedClass) {
        return (mob) -> new GoalHurtByTarget(mob, attackedClass);
    }

    static GoalSupplier<Mob> walkToLocation(final Location goal) {
        return (mob) -> new GoalWalkToLocation(mob, goal);
    }

    static GoalSupplier<Mob> walkToLocations(final List<Location> goals) {
        return (mob) -> new GoalWalkToLocation(mob, goals);
    }

    static GoalSupplier<Mob> avoidTarget(final List<Location> goals) {
        return (mob) -> new GoalWalkToLocation(mob, goals);
    }

    static GoalSupplier<Mob> avoidTarget(final Class<Entity> entityClass, final float radius, final double speed) {
        return (mob) -> new GoalAvoidTarget<>(mob, entityClass, radius, speed);
    }
}
