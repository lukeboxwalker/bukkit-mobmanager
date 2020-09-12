package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.EntityInformation;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDamageEvent;
import de.dicecraft.dicemobmanager.entity.event.CustomEntityDeathEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class NameChangeListener implements Listener {

    public void setName(LivingEntity entity, EntityInformation entityInformation) {
        AttributeInstance instance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (instance != null) {
            entity.setCustomName(entityInformation.getNameBuilder()
                    .hasName(entityInformation.getName())
                    .hasLevel(entityInformation.getLevel())
                    .hasNameColor(CustomColor.LIGHT_RED)
                    .hasMaxHealth((int) instance.getBaseValue())
                    .hasCurrentHealth((int) entity.getHealth())
                    .build()
            );
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamageNameChange(CustomEntityDamageEvent event) {
        setName((LivingEntity) event.getEntityDamageEvent().getEntity(), event.getEntityInformation());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeathNameChange(CustomEntityDeathEvent event) {
        setName(event.getEntityDeathEvent().getEntity(), event.getEntityInformation());
    }
}
