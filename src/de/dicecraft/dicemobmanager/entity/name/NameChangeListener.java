package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.EntityInformation;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDamageEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDeathEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEventHandler;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class NameChangeListener implements Listener {

    public void setName(LivingEntity entity, EntityInformation entityInformation) {
        setName(entity, (int) entity.getHealth(), entityInformation);
    }

    public void setName(LivingEntity entity, int currentHealth, EntityInformation entityInformation) {
        AttributeInstance instance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (instance != null) {
            entity.setCustomName(entityInformation.getNameBuilder()
                    .hasName(entityInformation.getName())
                    .hasLevel(entityInformation.getLevel())
                    .hasNameColor(CustomColor.LIGHT_RED)
                    .hasMaxHealth((int) instance.getBaseValue())
                    .hasCurrentHealth(currentHealth)
                    .build()
            );
        }
    }

    @CustomEventHandler(priority = EventPriority.LOWEST)
    public void onDamageNameChange(CustomEntityDamageEvent event) {
        int finalHealth = (int) (event.getEntity().getHealth() - event.getEntityDamageEvent().getFinalDamage());
        setName((LivingEntity) event.getEntityDamageEvent().getEntity(), Math.max(finalHealth, 0), event.getEntityInformation());
    }

    @CustomEventHandler(priority = EventPriority.LOWEST)
    public void onDeathNameChange(CustomEntityDeathEvent event) {
        setName(event.getEntityDeathEvent().getEntity(), event.getEntityInformation());
    }
}
