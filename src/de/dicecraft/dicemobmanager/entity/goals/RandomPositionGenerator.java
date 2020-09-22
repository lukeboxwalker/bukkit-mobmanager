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

    public static Vector getRandomVector(final Location location, final int xzRadius, final int yRadius, final Vector vector) {
        final Random random = DiceMobManager.getRandom();
        final Vector direction = location.toVector().subtract(vector);

        Vector result = null;
        Vector tempVector;
        for (int i = 0; i < MAX_ITERATION; i++) {
            tempVector = createVector(random, xzRadius, yRadius, direction);
            if (tempVector != null) {
                Vector possibleResult = location.toVector().add(tempVector);
                Block block = location.getWorld().getBlockAt(possibleResult.getBlockX(),
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
        if (vector != null) {
            double atan2 = Math.atan2(vector.getZ(), vector.getX()) - (Math.PI / 2);
            double input = atan2 + (double) (2.0F * random.nextFloat() - 1.0F) * (Math.PI / 2);
            double factor = Math.sqrt(random.nextDouble()) * 2.0 * 2.0 * (double) xzRadius;
            double x = -factor * Math.sin(input);
            double z = factor * Math.cos(input);
            if (Math.abs(x) <= (double) xzRadius && Math.abs(z) <= (double) xzRadius) {
                return new Vector(x, random.nextInt(2 * yRadius + 1) - yRadius, z);
            } else {
                return null;
            }
        } else {
            int x = random.nextInt(2 * xzRadius + 1) - xzRadius;
            int y = random.nextInt(2 * yRadius + 1) - yRadius;
            int z = random.nextInt(2 * xzRadius + 1) - xzRadius;
            return new Vector(x, y, z);
        }
    }
}
