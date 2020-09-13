package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDamageEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDeathEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEventHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

/**
 * Listener to update the name of an entity.
 * <p>
 * Entity name needs to be updated when it takes
 * damage to update a potential health representation.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class NameChangeListener implements Listener {

    /**
     * Sets the entities custom name.
     * <p>
     * Using the entities health as the current health
     * as well as the given entity information to set the name via
     * {@link NameChangeListener#setName(LivingEntity, double, EntityInformation)}
     *
     * @param entity            the entity to update the name for
     * @param entityInformation the information of the entity
     */
    public void setName(LivingEntity entity, EntityInformation entityInformation) {
        setName(entity, entity.getHealth(), entityInformation);
    }

    /**
     * Sets the entities custom name.
     * <p>
     * Using the entities information name supplier to generate
     * the name string to update the custom name see {@link NameSupplier}.
     *
     * @param entity            the entity to update the name for
     * @param currentHealth     the current health
     * @param entityInformation the information of the entity
     */
    public void setName(LivingEntity entity, double currentHealth, EntityInformation entityInformation) {
        String name = entityInformation.getNameSupplier().supply(entity, currentHealth, entityInformation);
        entity.setCustomName(name);
    }

    /**
     * Event handler when custom entity takes damage.
     * <p>
     * Calculates the final health the entity has after it was damaged.
     * Sets the name by using {@link NameChangeListener#setName(LivingEntity, double, EntityInformation)}
     * Using highest priority (monitor) to be the last event handler to act on the damage event.
     *
     * @param event the {@link CustomEntityDamageEvent} to listen to
     */
    @CustomEventHandler(priority = EventPriority.MONITOR)
    public void onDamageNameChange(CustomEntityDamageEvent event) {
        double finalHealth = (event.getEntity().getHealth() - event.getEntityDamageEvent().getFinalDamage());
        setName((LivingEntity) event.getEntityDamageEvent().getEntity(), Math.max(finalHealth, 0), event.getEntityInformation());
    }

    /**
     * Event handler when custom entity takes damage.
     * <p>
     * Sets the name by using {@link NameChangeListener#setName(LivingEntity, EntityInformation)}
     * Using highest priority (monitor) to be the last event handler to act on the death event.
     *
     * @param event the {@link CustomEntityDeathEvent} to listen to
     */
    @CustomEventHandler(priority = EventPriority.MONITOR)
    public void onDeathNameChange(CustomEntityDeathEvent event) {
        setName(event.getEntityDeathEvent().getEntity(), event.getEntityInformation());
    }
}
