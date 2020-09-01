package de.dicecraft.dicemobmanager.entity.pathfinder.navigation;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

public interface CustomNavigation {

    void resetPathEntity();

    boolean checkPath(final LivingEntity entity, final double d);

    CustomPathEntity createPathEntity(final LivingEntity entity, final int i);

    CustomPathEntity createPathEntity(final Location location, final int i);

    void walkPathEntity(final CustomPathEntity customPathEntity, double speed);

    boolean hasNoPathEntity();
}
