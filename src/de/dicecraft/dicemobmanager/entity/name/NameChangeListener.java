package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDamageEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDeathEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEventHandler;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class NameChangeListener implements Listener {

    public void setName(LivingEntity entity, EntityInformation entityInformation) {
        setName(entity, entity.getHealth(), entityInformation);
    }

    public void setName(LivingEntity entity, double currentHealth, EntityInformation entityInformation) {
        String name = entityInformation.getNameSupplier().supply(entity, currentHealth, entityInformation);
        entity.setCustomName(name);
    }

    @CustomEventHandler(priority = EventPriority.HIGHEST)
    public void onDamageNameChange(CustomEntityDamageEvent event) {
        double finalHealth = (event.getEntity().getHealth() - event.getEntityDamageEvent().getFinalDamage());
        setName((LivingEntity) event.getEntityDamageEvent().getEntity(), Math.max(finalHealth, 0), event.getEntityInformation());
    }

    @CustomEventHandler(priority = EventPriority.HIGHEST)
    public void onDeathNameChange(CustomEntityDeathEvent event) {
        setName(event.getEntityDeathEvent().getEntity(), event.getEntityInformation());
    }
}
