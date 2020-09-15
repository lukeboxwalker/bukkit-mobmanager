package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import javax.annotation.Nonnull;
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

    @Nonnull
    NameSupplier getNameSupplier();

    @Nonnull
    List<DeathDrop> getDeathDrops();

    String getName();

    int getLevel();

    boolean isAggressive();

    /**
     * Called when the entity is ticked
     * by the mob manager.
     *
     * @param entity the bukkit entity.
     */
    void onEntityTick(Entity entity);

    /**
     * Called when the entity is damaged.
     *
     * @param event the bukkit event.
     */
    void onEntityDamage(EntityDamageEvent event);

    /**
     * Called when the entity dies
     * by the mob manager.
     *
     * @param event the bukkit event.
     */
    void onEntityDeath(EntityDeathEvent event);

    /**
     * Called when the entity dies
     * by the mob manager.
     *
     * @param event the bukkit event.
     */
    void onEntitySpawn(EntitySpawnEvent event);

    /**
     * Called when the entity drops an item.
     *
     * @param deathDrop the loot drop.
     */
    void onItemDrop(DeathDrop deathDrop);
}
