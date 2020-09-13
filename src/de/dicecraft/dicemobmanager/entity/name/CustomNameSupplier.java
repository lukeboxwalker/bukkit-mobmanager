package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;

/**
 * Name supplier for an entity.
 * <p>
 * Creates the name String from given information.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class CustomNameSupplier implements NameSupplier {

    /**
     * Supplying the new name string.
     * <p>
     * Creates new name from given information using
     * the entity, the currentHealth as well as the {@link EntityInformation}
     * <p>
     * Coloring the current health green
     * when its above 50% of the maximum heath, otherwise
     * its shown in yellow.
     *
     * @param entity        to create name for
     * @param currentHealth of the entity
     * @param information   of the entity
     * @return new custom name for the given entity
     */
    @Override
    public String supply(final LivingEntity entity, final double currentHealth, final EntityInformation information) {
        AttributeInstance instance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (instance != null) {
            final double maxHealth = instance.getBaseValue();
            StringBuilder builder = new StringBuilder()
                    .append("§8[").append("§7Lv").append(information.getLevel())
                    .append("§8] ").append("§c").append(information.getName()).append(" ");
            double percent = currentHealth / maxHealth * 100;
            if (percent > 50) {
                builder.append("§a").append((int) currentHealth);
            } else {
                builder.append("§e").append((int) currentHealth);
            }
            builder.append("§f/").append("§a").append((int) maxHealth);
            return builder.toString();
        }
        return entity.getCustomName();
    }
}
