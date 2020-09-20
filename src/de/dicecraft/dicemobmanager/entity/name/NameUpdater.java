package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.builder.ProtoNamedEntity;
import org.bukkit.entity.LivingEntity;

/**
 * Name supplier for an entity.
 * <p>
 * Creates the name String from given information.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface NameUpdater {

    /**
     * Supplying the new name string.
     * <p>
     * Creates new name from given information using
     * the entity, the currentHealth as well as the {@link ProtoEntity}
     *
     * @param entity        to create name for
     * @param currentHealth of the entity
     * @return new custom name for the given entity
     */
    String buildName(final LivingEntity entity, final double currentHealth);

    void updateName(final LivingEntity entity);

    void updateName(final LivingEntity entity, final double currentHealth);
}
