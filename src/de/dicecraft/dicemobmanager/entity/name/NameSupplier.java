package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import org.bukkit.entity.LivingEntity;

/**
 * Name supplier for an entity.
 * <p>
 * Creates the name String from given information.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
@FunctionalInterface
public interface NameSupplier {

    /**
     * Supplying the new name string.
     * <p>
     * Creates new name from given information using
     * the entity, the currentHealth as well as the {@link EntityInformation}
     *
     * @param entity to create name for
     * @param currentHealth of the entity
     * @param information of the entity
     * @return new custom name for the given entity
     */
    String supply(final LivingEntity entity, final double currentHealth, final EntityInformation information);
}
