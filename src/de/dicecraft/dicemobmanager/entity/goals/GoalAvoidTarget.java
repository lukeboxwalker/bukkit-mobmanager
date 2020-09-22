package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class GoalAvoidTarget<T extends Entity> implements Goal<Mob> {

    private static final double Y_RADIUS = 3D;
    private static final int XYZ_RADIUS = 8;

    private final Mob mob;

    private final double speed;
    private T avoidTarget;
    private final float radius;
    private Pathfinder.PathResult pathResult;
    private final Class<T> avoidEntityClass;
    private final Predicate<Entity> entityPredicate;

    public GoalAvoidTarget(final Mob mob, final Class<T> avoidEntityClass, final float radius, final double speed) {
        this(mob, avoidEntityClass, radius, speed, EntitySelector.IS_PLAYER_IN_SURVIVAL);
    }

    public GoalAvoidTarget(final Mob mob, final Class<T> avoidEntityClass, final float radius,
                           final double speed, final Predicate<Entity> entityPredicate) {
        this.mob = mob;
        this.avoidEntityClass = avoidEntityClass;
        this.radius = radius;
        this.speed = speed;
        this.entityPredicate = entityPredicate;
    }

    @Override
    public boolean shouldActivate() {
        Optional<T> nearestEntity = getNearestAvoidableEntity();
        if (nearestEntity.isPresent()) {
            avoidTarget = nearestEntity.get();
            Vector avoidTargetVector = avoidTarget.getLocation().toVector();
            Vector vector = RandomPositionGenerator.getRandomVector(mob, XYZ_RADIUS, XYZ_RADIUS, avoidTargetVector);
            if (vector == null) {
                return false;
            }
            double avoidTargetToVector = distanceSquared(avoidTargetVector, vector);
            double avoidTargetToMob = distanceSquared(avoidTargetVector, mob.getLocation().toVector());
            if (avoidTargetToVector < avoidTargetToMob) {
                return false;
            } else {
                pathResult = mob.getPathfinder().findPath(vector.toLocation(mob.getWorld()));
                return pathResult != null;
            }
        } else {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private Optional<T> getNearestAvoidableEntity() {
        Optional<T> result = Optional.empty();
        Location mobLocation = mob.getLocation();
        Collection<T> nearByEntities = (Collection<T>) mob.getWorld()
                .getNearbyEntitiesByType(avoidEntityClass, mobLocation, radius, Y_RADIUS, radius, entityPredicate);
        double shortestDistance = radius * radius * radius;
        Vector vector1 = mob.getLocation().toVector();
        for (T entity : nearByEntities) {
            Vector vector2 = entity.getLocation().toVector();
            double distance = distanceSquared(vector1, vector2);
            if (distance < shortestDistance && entityPredicate.test(entity)) {
                shortestDistance = distance;
                result = Optional.of(entity);
            }
        }
        return result;
    }

    private double distanceSquared(Vector vector1, Vector vector2) {
        double x = vector1.getX() - vector2.getX();
        double y = vector1.getY() - vector2.getY();
        double z = vector1.getZ() - vector2.getZ();
        return x * x + y * y + z * z;
    }

    @Override
    public boolean shouldStayActive() {
        Pathfinder.PathResult pathResult = mob.getPathfinder().getCurrentPath();
        if (pathResult != null && pathResult.getPoints().size() == pathResult.getNextPointIndex()) {
            avoidTarget = null;
            if (this.shouldActivate()) {
                this.start();
            }
        }
        return mob.getPathfinder().hasPath();
    }

    @Override
    public void start() {
        mob.getPathfinder().moveTo(pathResult, speed);
    }

    @Override
    public void stop() {
        this.avoidTarget = null;
    }

    /**
     * A unique key that identifies this type of goal.
     *
     * @return the goal key
     */
    @Nonnull
    @Override
    public GoalKey<Mob> getKey() {
        return CustomGoal.AVOID_TARGET;
    }

    /**
     * Specifies the goal types of this goal.
     * <p>
     * Walking to a location is specified by a {@link GoalType#MOVE}
     *
     * @return a list of all applicable types for this goal
     */
    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}
