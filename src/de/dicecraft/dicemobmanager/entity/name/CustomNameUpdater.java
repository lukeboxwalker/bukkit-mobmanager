package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.ProtoNamedEntity;
import org.bukkit.ChatColor;
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
public class CustomNameUpdater implements NameUpdater {

    private static final int HUNDRED = 100;
    private static final int FIFTY = 50;

    private static final String DARK_GRAY = "" + ChatColor.COLOR_CHAR + ChatColor.DARK_GRAY.getChar();
    private static final String LIGHT_GRAY = "" + ChatColor.COLOR_CHAR + ChatColor.GRAY.getChar();
    private static final String LIGHT_RED = "" + ChatColor.COLOR_CHAR + ChatColor.RED.getChar();
    private static final String WHITE = "" + ChatColor.COLOR_CHAR + ChatColor.WHITE.getChar();
    private static final String LIGHT_GREEN = "" + ChatColor.COLOR_CHAR + ChatColor.GREEN.getChar();
    private static final String YELLOW = "" + ChatColor.COLOR_CHAR + ChatColor.YELLOW.getChar();

    private final ProtoNamedEntity protoEntity;

    public CustomNameUpdater(final ProtoNamedEntity protoEntity) {
        this.protoEntity = protoEntity;
    }

    /**
     * Supplying the new name string.
     * <p>
     * Creates new name from given information using
     * the entity and the currentHealth.
     * <p>
     * Coloring the current health green
     * when its above 50% of the maximum heath, otherwise
     * its shown in yellow.
     *
     * @param entity        to create name for
     * @param currentHealth of the entity
     * @return new custom name for the given entity
     */
    @Override
    public String buildName(final LivingEntity entity, final double currentHealth) {
        AttributeInstance instance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double health = currentHealth < 1 ? currentHealth < 0 ? 0 : 1 : currentHealth;
        if (instance != null) {
            final double maxHealth = instance.getBaseValue();

            StringBuilder builder = new StringBuilder()
                    .append(DARK_GRAY).append("[").append(LIGHT_GRAY).append("Lv").append(protoEntity.getLevel())
                    .append(DARK_GRAY).append("] ").append(LIGHT_RED).append(protoEntity.getName()).append(" ");
            double percent = health / maxHealth * HUNDRED;
            if (percent > FIFTY) {
                builder.append(LIGHT_GREEN).append((int) health);
            } else {
                builder.append(YELLOW).append((int) health);
            }
            builder.append(WHITE).append("/")
                    .append(LIGHT_GREEN)
                    .append((int) maxHealth)
                    .append(LIGHT_RED).append("❤");
            return builder.toString();
        }
        return entity.getCustomName();
    }

    public String buildName(final LivingEntity entity) {
        return buildName(entity, entity.getHealth());
    }

    @Override
    public void updateName(LivingEntity entity) {
        updateName(entity, entity.getHealth());
    }

    @Override
    public void updateName(LivingEntity entity, double currentHealth) {
        entity.setCustomName(buildName(entity, currentHealth));
    }
}
