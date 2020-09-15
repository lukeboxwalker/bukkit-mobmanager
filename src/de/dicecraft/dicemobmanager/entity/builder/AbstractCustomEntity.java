package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
public abstract class AbstractCustomEntity implements CustomEntity {

    /**
     * Sets the entities custom name.
     * <p>
     * Using the entities information name supplier to generate
     * the name string to update the custom name see {@link NameSupplier}.
     *
     * @param entity            the entity to update the name for
     * @param currentHealth     the current health
     */
    private void updateName(LivingEntity entity, double currentHealth) {
        String name = getNameSupplier().supply(entity, currentHealth, this);
        entity.setCustomName(name);
    }

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
        if (event.getEntity() instanceof LivingEntity) {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            double finalHealth = (entity.getHealth() - event.getFinalDamage());
            updateName(entity, Math.max(finalHealth, 0));
        }
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
        if (event.getEntity() instanceof LivingEntity) {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            updateName(entity, entity.getHealth());
        }
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
