package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import de.dicecraft.dicemobmanager.utils.PositionUtils;
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

    /**
     * Creates new GoalAvoidTarget.
     * <p>
     * The Goal is used to avoid a specific entity type.
     * It will calculate a spot so the entity that uses this goal can avoid
     * the target type.
     *
     * @param mob              the mob that uses the goal
     * @param avoidEntityClass the entity class type to avoid
     * @param radius           the radius in which the mob will avoid the type
     * @param speed            the speed the mob will run away
     * @param entityPredicate  the predicate to check to find nearby entites
     */
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
        final Optional<T> nearestEntity = getNearestAvoidableEntity();
        if (nearestEntity.isPresent()) {
            avoidTarget = nearestEntity.get();
            final Vector avoidTargetVector = avoidTarget.getLocation().toVector();
            final Vector vector = RandomPositionGenerator
                    .getRandomVector(mob, XYZ_RADIUS, XYZ_RADIUS, avoidTargetVector);
            if (vector == null) {
                return false;
            }
            final double avoidTargetToVector = PositionUtils.distanceSquared(avoidTargetVector, vector);
            final Vector mobVector = mob.getLocation().toVector();
            final double avoidTargetToMob = PositionUtils.distanceSquared(avoidTargetVector, mobVector);
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
        final Location mobLocation = mob.getLocation();
        final Collection<T> nearByEntities = (Collection<T>) mob.getWorld()
                .getNearbyEntitiesByType(avoidEntityClass, mobLocation, radius, Y_RADIUS, radius, entityPredicate);
        double shortestDistance = radius * radius * radius;
        final Vector vector1 = mob.getLocation().toVector();
        for (final T entity : nearByEntities) {
            final Vector vector2 = entity.getLocation().toVector();
            final double distance = PositionUtils.distanceSquared(vector1, vector2);
            if (distance < shortestDistance && entityPredicate.test(entity)) {
                shortestDistance = distance;
                result = Optional.of(entity);
            }
        }
        return result;
    }


    @Override
    public boolean shouldStayActive() {
        final Pathfinder.PathResult pathResult = mob.getPathfinder().getCurrentPath();
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
