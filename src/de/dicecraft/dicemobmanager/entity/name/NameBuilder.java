package de.dicecraft.dicemobmanager.entity.name;

/**
 * Builds a custom entity name.
 * <p>
 * The name builder is used to create a custom
 * string name based on the entities name, a color
 * see {@link CustomColor} for the name, a level for the entity,
 * as well as the current and max health the entity have.
 * <p>
 * Name will be formatted as following:
 * [Lv{level}] {name} {currentHealth}/{maxHealth}
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class NameBuilder implements CustomNameBuilder {

    private int level;
    private int maxHealth;
    private int health;
    private String name;
    private CustomColor customColor;

    /**
     * Specifies the name of the entity.
     * <p>
     * Manipulating {@link NameBuilder#name}
     *
     * @param name the name of the entity
     * @return builder to continue
     */
    @Override
    public CustomNameBuilder hasName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Specifies the name color.
     * <p>
     * The custom color is used to color the
     * name. See also {@link CustomColor}
     * <p>
     * Manipulating {@link NameBuilder#customColor}
     *
     * @param customColor the name of the entity
     * @return builder to continue
     */
    @Override
    public CustomNameBuilder hasNameColor(CustomColor customColor) {
        this.customColor = customColor;
        return this;
    }

    /**
     * Specifies the level of the entity.
     * <p>
     * Manipulating {@link NameBuilder#level}
     *
     * @param level the level of the entity
     * @return builder to continue
     */
    @Override
    public CustomNameBuilder hasLevel(int level) {
        this.level = level;
        return this;
    }

    /**
     * Specifies the maximum heath of the entity.
     * <p>
     * Manipulating {@link NameBuilder#maxHealth}
     *
     * @param maxHealth the initial maximum health of the entity
     * @return builder to continue
     */
    @Override
    public CustomNameBuilder hasMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    /**
     * Specifies the current heath of the entity.
     * <p>
     * Manipulating {@link NameBuilder#health}
     *
     * @param health the current health of the entity
     * @return builder to continue
     */
    @Override
    public CustomNameBuilder hasCurrentHealth(int health) {
        this.health = health;
        return this;
    }

    /**
     * Creates a new string name.
     * <p>
     * Using all given information to create
     * a new custom name. Coloring the current health green
     * when its above 50% of the maximum heath, otherwise
     * its shown in yellow.
     * <p>
     * Coloring name parts with {@link CustomColor}
     *
     * @return new custom string name
     */
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder()
                .append(CustomColor.GREY.color("["))
                .append(CustomColor.LIGHT_GREY.color("Lv" + level))
                .append(CustomColor.GREY.color("] "))
                .append(customColor.color(name + " "));
        double percent = (double) health / (double) maxHealth * 100;
        if (percent > 50) {
            builder.append(CustomColor.LIGHT_GREEN.color(health));
        } else {
            builder.append(CustomColor.LIGHT_YELLOW.color(health));
        }
        builder.append(CustomColor.WHITE.color("/")).append(CustomColor.LIGHT_GREEN.color(maxHealth));
        return builder.toString();
    }
}
