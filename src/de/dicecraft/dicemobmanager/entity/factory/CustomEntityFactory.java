package de.dicecraft.dicemobmanager.entity.factory;

import de.dicecraft.dicemobmanager.entity.builder.EntityConfiguration;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

/**
 * Creates and spawns an Entity.
 * <p>
 * For Entity configuration also see {@link EntityConfiguration}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface CustomEntityFactory {


    /**
     * Creates and spawns an Entity at a given location.
     *
     * @param configuration to configure the entity
     * @param plugin        the plugin that spawns the entity
     * @throws EntityCreationException when entity creation fails.
     */
    Entity spawnEntity(final EntityConfiguration configuration, final Plugin plugin) throws EntityCreationException;

}
