package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

public class CustomNameSupplier implements NameSupplier {

    @Override
    public String supply(final LivingEntity entity, final int currentHealth, final EntityInformation information) {
        AttributeInstance instance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (instance != null) {
            final double maxHealth =instance.getBaseValue();
            StringBuilder builder = new StringBuilder()
                    .append("§8[").append("§7Lv").append(information.getLevel())
                    .append("§8] ").append("§c").append(information.getName()).append(" ");
            double percent = currentHealth / maxHealth * 100;
            if (percent > 50) {
                builder.append("§a").append(currentHealth);
            } else {
                builder.append("§e").append(currentHealth);
            }
            builder.append("§f/").append("§a").append(maxHealth);
            return builder.toString();
        }
        return entity.getCustomName();
    }
}
