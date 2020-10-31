package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.event.DamageEvent;
import de.dicecraft.dicemobmanager.entity.event.DeathEvent;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import de.dicecraft.dicemobmanager.entity.event.TickEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Mob;
import org.bukkit.potion.PotionEffect;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Set;

/**
 * Custom information of an entity.
 * <p>
 * Contains all important information for an entity.
 * For example the level, the custom name, the name
 * supplier and the death loot.
 *
 * @param <T> the type of the mob
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface ProtoEntity<T extends Mob> extends ProtoNamedEntity, ProtoGoalEntity {

    /**
     * Defines whether the entity should
     * burn at day.
     * <p>
     * Can only be applied on mobs providing
     * a setter method eg. Zombie or Phantom
     *
     * @return if the entity should burn at day.
     */
    default boolean shouldBurnInDay() {
        return false;
    }

    @Nonnull
    Set<PotionEffect> getPotionEffects();

    /**
     * Gets the death drops.
     * <p>
     * Contains all items the entity could
     * drop.
     *
     * @return a list of death drops.
     */
    @Nonnull
    Set<DeathDrop> getDeathDrops();

    @Nonnull
    Equipment getEquipment();

    @Nonnull
    CustomType<T> getCustomType();

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
     * @param event the bukkit event.
     * @param mob   the mob
     */
    void onEntityTick(TickEvent event, T mob);

    /**
     * Called when the entity is damaged.
     *
     * @param event the bukkit event.
     * @param mob   the mob
     */
    void onEntityDamage(DamageEvent event, T mob);

    /**
     * Called when the entity dies
     * by the mob manager.
     *
     * @param event the bukkit event.
     * @param mob   the mob
     */
    void onEntityDeath(DeathEvent event, T mob);

    /**
     * Called when the entity dies
     * by the mob manager.
     *
     * @param event the bukkit event.
     * @param mob   the mob
     */
    void onEntitySpawn(SpawnEvent event, T mob);

    /**
     * Called when the entity drops an item.
     *
     * @param event the loot drop event.
     * @param mob   the mob
     */
    void onItemDrop(ItemDropEvent event, T mob);
}
