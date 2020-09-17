package de.dicecraft.dicemobmanager.entity.goals;

import de.dicecraft.dicemobmanager.DiceMobManager;

import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.util.Vector;

import java.util.Random;

public final class RandomPositionGenerator {

    public static Vector getRandomVector(Mob mob, int xzRadius, int yRadius, Vector vector) {
        Vector direction = mob.getLocation().toVector().subtract(vector);

        Random random = DiceMobManager.getRandom();

        boolean foundVector = false;
        Vector result = mob.getLocation().toVector();

        for (int i = 0; i < 10; i++) {
            Vector createdVector = createVector(random, xzRadius, yRadius, direction);
            if (createdVector != null) {
                Vector possibleResult = mob.getLocation().toVector().add(createdVector);
                Block block = mob.getWorld().getBlockAt(possibleResult.getBlockX(), possibleResult.getBlockY() - 1, possibleResult.getBlockZ());
                if (block.getType().isSolid()) {
                    result = possibleResult;
                    foundVector = true;
                }
            }
        }

        if (foundVector) {
            return result;
        } else {
            return null;
        }
    }

    private static Vector createVector(Random random, int xzRadius, int yRadius, Vector vector) {
        if (vector != null) {
            double atan2 = Math.atan2(vector.getZ(), vector.getX()) - (Math.PI / 2);
            double input = atan2 + (double) (2.0F * random.nextFloat() - 1.0F) * (Math.PI / 2);
            double factor = Math.sqrt(random.nextDouble()) * 4.0 * (double) xzRadius;
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
