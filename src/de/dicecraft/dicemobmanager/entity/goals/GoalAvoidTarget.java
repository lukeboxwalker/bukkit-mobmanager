package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.Pathfinder;
import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import de.dicecraft.dicemobmanager.DiceMobManager;
import net.minecraft.server.v1_16_R2.EntityCreature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public class GoalAvoidTarget<T extends Entity> implements Goal<Mob> {

    private static final GoalKey<Mob> GOAL_KEY = GoalKey.of(Mob.class, DiceMobManager.createNameSpacedKey("avoid_target"));

    private static final int ACTIVATE_TICK = 20;
    private final Mob mob;

    protected EntityCreature a;
    private final double speed;
    protected T avoidTarget;
    protected final float radius;
    protected Pathfinder.PathResult pathResult;
    protected final Class<T> avoidEntityClass;
    protected final Predicate<Entity> entityPredicate;
    private int tickCounter;

    public GoalAvoidTarget(final Mob mob, Class<T> avoidEntityClass, float radius, double speed) {
        this(mob, avoidEntityClass, radius, speed, EntitySelector.isPlayerInSurvival);
    }

    public GoalAvoidTarget(Mob mob, Class<T> avoidEntityClass, float radius, double speed, Predicate<Entity> entityPredicate) {
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
            Vector vector = RandomPositionGenerator.getRandomVector(mob, 8, 7, avoidTarget.getLocation().toVector());
            if (vector == null) {
                return false;
            }
            double avoidTargetToVector = distanceSquared(avoidTarget.getLocation().toVector(), vector);
            double avoidTargetToMob = distanceSquared(avoidTarget.getLocation().toVector(), mob.getLocation().toVector());
            if (avoidTargetToVector < avoidTargetToMob) {
                return false;
            }  else {
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
        Collection<T> nearByEntities = (Collection<T>) mob.getWorld().getNearbyEntitiesByType(avoidEntityClass, mob.getLocation(), radius, 3.0D, radius, entityPredicate);
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
        return GOAL_KEY;
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
