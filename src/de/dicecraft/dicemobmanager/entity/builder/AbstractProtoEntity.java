package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

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
public abstract class AbstractProtoEntity implements ProtoEntity {

    /**
     * Called when the entity is ticked
     * by the mob manager.
     *
     * @param entity the bukkit entity.
     */
    @Override
    public void onEntityTick(Entity entity) {
    }

    /**
     * Called when the entity is damaged.
     *
     * @param event the bukkit event.
     */
    @Override
    public void onEntityDamage(EntityDamageEvent event) {
    }

    /**
     * Called when the entity dies
     * by the mob manager.
     *
     * @param event the bukkit event.
     */
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
    }

    /**
     * Called when the entity dies
     * by the mob manager.
     *
     * @param event the bukkit event.
     */
    @Override
    public void onEntitySpawn(EntitySpawnEvent event) {
    }

    /**
     * Called when the entity drops an item.
     *
     * @param deathDrop the loot drop.
     */
    @Override
    public void onItemDrop(DeathDrop deathDrop) {
    }
}
