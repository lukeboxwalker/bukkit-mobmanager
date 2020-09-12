package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.CustomEntities;
import de.dicecraft.dicemobmanager.entity.EntityInformation;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Optional;

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
    public void onDamageNameChange(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            Optional<EntityInformation> optional = CustomEntities.getInformation(event.getEntity());
            optional.ifPresent(entityInformation -> setName((LivingEntity) event.getEntity(), entityInformation));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeathNameChange(EntityDeathEvent event) {
        Optional<EntityInformation> optional = CustomEntities.getInformation(event.getEntity());
        optional.ifPresent(entityInformation -> setName(event.getEntity(), entityInformation));

    }
}
