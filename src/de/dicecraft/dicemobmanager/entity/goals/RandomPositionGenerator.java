package de.dicecraft.dicemobmanager.entity.goals;

import de.dicecraft.dicemobmanager.DiceMobManager;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

import java.util.Random;

public final class RandomPositionGenerator {

    private static final int MAX_ITERATION = 10;

    private RandomPositionGenerator() {
    }

    public static Vector getRandomVector(final Mob mob, final int xzRadius, final int yRadius, final Vector vector) {
        return getRandomVector(mob.getLocation(), xzRadius, yRadius, vector);
    }

    /**
     * Generates a random position vector.
     * <p>
     * The position that is generated will be around the given location
     * and in the bound of the given radius.
     * The location is calculated in the direction of the given vector.
     *
     * @param location the center of the calculation
     * @param xzRadius the xz radius
     * @param yRadius  the y radius
     * @param vector   the direction vector
     * @return a new random position vector
     */
    public static Vector getRandomVector(final Location location, final int xzRadius,
                                         final int yRadius, final Vector vector) {
        final Random random = DiceMobManager.getRandom();
        final Vector direction = location.toVector().subtract(vector);

        Vector result = null;
        Vector tempVector;
        for (int i = 0; i < MAX_ITERATION; i++) {
            tempVector = createVector(random, xzRadius, yRadius, direction);
            if (tempVector != null) {
                final Vector possibleResult = location.toVector().add(tempVector);
                final Block block = location.getWorld().getBlockAt(possibleResult.getBlockX(),
                        possibleResult.getBlockY() - 1, possibleResult.getBlockZ());
                if (block.getType().isSolid()) {
                    result = possibleResult;
                }
            }
        }
        return result;
    }

    private static Vector createVector(final Random random, final int xzRadius,
                                       final int yRadius, final Vector vector) {
        if (vector == null) {
            final int posX = random.nextInt(2 * xzRadius + 1) - xzRadius;
            final int posY = random.nextInt(2 * yRadius + 1) - yRadius;
            final int posZ = random.nextInt(2 * xzRadius + 1) - xzRadius;
            return new Vector(posX, posY, posZ);
        } else {
            final double atan2 = Math.atan2(vector.getZ(), vector.getX()) - (Math.PI / 2);
            final double input = atan2 + (double) (2.0F * random.nextFloat() - 1.0F) * (Math.PI / 2);
            final double factor = Math.sqrt(random.nextDouble()) * 2.0 * 2.0 * (double) xzRadius;
            final double posX = -factor * Math.sin(input);
            final double posZ = factor * Math.cos(input);
            if (Math.abs(posX) <= (double) xzRadius && Math.abs(posZ) <= (double) xzRadius) {
                return new Vector(posX, random.nextInt(2 * yRadius + 1) - yRadius, posZ);
            } else {
                return null;
            }
        }
    }
}
