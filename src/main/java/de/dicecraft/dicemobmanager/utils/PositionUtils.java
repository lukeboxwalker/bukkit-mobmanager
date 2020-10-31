package de.dicecraft.dicemobmanager.utils;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public final class PositionUtils {

    private PositionUtils() {
    }

    /**
     * Calculates the squared distance between two vectors.
     *
     * @param first  the first vector
     * @param second the second vector
     * @return the squared distance
     */
    public static double distanceSquared(final Vector first, final Vector second) {
        final double posX = first.getX() - second.getX();
        final double posY = first.getY() - second.getY();
        final double posZ = first.getZ() - second.getZ();
        return posX * posX + posY * posY + posZ * posZ;
    }


    public static double distanceSquared(final Location first, final Location second) {
        return distanceSquared(first.toVector(), second.toVector());
    }

    public static double distanceSquared(final Entity first, final Entity second) {
        return distanceSquared(first.getLocation(), second.getLocation());
    }
}
