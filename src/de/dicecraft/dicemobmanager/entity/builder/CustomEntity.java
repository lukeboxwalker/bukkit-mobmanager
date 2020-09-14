package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;

import java.util.List;

/**
 * Custom information of an entity.
 * <p>
 * Contains all important information for an entity.
 * For example the level, the custom name, the name
 * supplier and the death loot.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface CustomEntity {

    NameSupplier getNameSupplier();

    String getName();

    int getLevel();

    List<DeathDrop> getDeathDrops();

    boolean isAggressive();
}
