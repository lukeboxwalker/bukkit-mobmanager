package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.ProtoNamedEntity;
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

    private static final int THOUSAND = 1_000;
    private static final int MILLION = 1_000_000;
    private static final int HUNDRED = 100;
    private static final int FIFTY = 50;

    private static final String COLOR_PREFIX = String.valueOf(ChatColor.COLOR_CHAR);
    private static final String DARK_GRAY = COLOR_PREFIX + ChatColor.DARK_GRAY.getChar();
    private static final String LIGHT_GRAY = COLOR_PREFIX + ChatColor.GRAY.getChar();
    private static final String LIGHT_RED = COLOR_PREFIX + ChatColor.RED.getChar();
    private static final String WHITE = COLOR_PREFIX + ChatColor.WHITE.getChar();
    private static final String LIGHT_GREEN = COLOR_PREFIX + ChatColor.GREEN.getChar();
    private static final String YELLOW = COLOR_PREFIX + ChatColor.YELLOW.getChar();

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
        final AttributeInstance instance = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        final double health = currentHealth < 1 ? currentHealth < 0 ? 0 : 1 : currentHealth;
        if (instance != null) {
            final double maxHealth = instance.getBaseValue();

            final StringBuilder builder = new StringBuilder()
                    .append(DARK_GRAY).append('[').append(LIGHT_GRAY).append("Lv").append(protoEntity.getLevel())
                    .append(DARK_GRAY).append("] ").append(LIGHT_RED).append(protoEntity.getName()).append(' ');
            final double percent = health / maxHealth * HUNDRED;
            if (percent > FIFTY) {
                builder.append(LIGHT_GREEN).append(formatHealth((int) health));
            } else {
                builder.append(YELLOW).append(formatHealth((int) health));
            }
            builder.append(WHITE).append("/")
                    .append(LIGHT_GREEN)
                    .append(formatHealth((int) maxHealth))
                    .append(LIGHT_RED).append("❤");
            return builder.toString();
        }
        return entity.getCustomName();
    }

    /**
     * Formats the given health.
     * <p>
     * Examples:
     * 1000 -> 1k
     * 100000 -> 100k
     * 1000000 -> 1m
     *
     * @param number the health to format
     * @return the formatted health string.
     */
    public String formatHealth(final int number) {
        if (number >= MILLION) {
            return number / MILLION + "m";
        } else if (number >= THOUSAND) {
            return number / THOUSAND + "k";
        } else {
            return String.valueOf(number);
        }
    }

    @Override
    public String buildName(final LivingEntity entity) {
        return buildName(entity, entity.getHealth());
    }

    @Override
    public void updateName(final LivingEntity entity) {
        updateName(entity, entity.getHealth());
    }

    @Override
    public void updateName(final LivingEntity entity, final double currentHealth) {
        entity.setCustomName(buildName(entity, currentHealth));
    }
}
