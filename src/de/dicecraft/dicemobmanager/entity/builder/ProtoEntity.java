package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.CustomEquipment;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public interface ProtoEntity extends ProtoNamedEntity, ProtoGoalEntity {

    @Nonnull
    default Set<PotionEffect> getPotionEffects() {
        return new HashSet<>();
    }

    /**
     * Gets the death drops.
     * <p>
     * Contains all items the entity could
     * drop.
     *
     * @return a list of death drops.
     */
    @Nonnull
    default List<DeathDrop> getDeathDrops() {
        return new ArrayList<>();
    }

    @Nonnull
    default Equipment getEquipment() {
        return new CustomEquipment();
    }

    @Nonnull
    EntityType getEntityType();

    /**
     * Gets all attributes the entity
     * is has.
     *
     * @return the attribute map which relates the
     * attribute with there corresponding double value
     */
    @Nonnull
    Map<Attribute, Double> getAttributeMap();

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
