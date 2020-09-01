package de.dicecraft.dicemobmanager.adapter;

import de.dicecraft.dicemobmanager.entity.CustomEntity;
import de.dicecraft.dicemobmanager.entity.builder.EntityConfiguration;
import org.bukkit.Location;

/**
 * Creates and spawns {@link CustomEntity}.
 *
 * Using NMS and reflections to wrap the original
 * minecraft entities.
 * For Entity configuration also see {@link EntityConfiguration}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface CustomEntityFactory {

    /**
     * Creates a {@link CustomEntity}.
     * <p>
     * Entity is created by using a valid {@link EntityConfiguration} which
     * consists all information for the entity. The configuration is used to identify the
     * CustomType of the entity and to apply all strategies and behaviors the entity should
     * consist of.
     *
     * @param configuration to configure the entity
     * @param <T>           the specific type of the CustomEntity if needed (in most cases this is {@link CustomEntity})
     * @return the CustomEntity that was created
     * @throws EntityCreationException when entity creation failed
     */
    <T extends CustomEntity> T createCustomEntity(final EntityConfiguration<T> configuration) throws EntityCreationException;

    /**
     * Creates and spawns a {@link CustomEntity} at a given location.
     * <p>
     *
     * @param configuration to configure the entity
     * @param location     to define where to spawn the entity
     * @throws EntityCreationException when entity creation failed
     */
    <T extends CustomEntity> T spawnCustomEntity(final EntityConfiguration<T> configuration, final Location location) throws EntityCreationException;

    /**
     * Spawns a {@link CustomEntity} at a given location.
     * <p>
     *
     * @param customEntity the entity to spawn
     * @param location     to define where to spawn the entity
     */
    void spawnCustomEntity(final CustomEntity customEntity, final Location location);
}
