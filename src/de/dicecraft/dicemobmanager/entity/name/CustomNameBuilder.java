package de.dicecraft.dicemobmanager.entity.name;

/**
 * Builds a custom entity name.
 * <p>
 * The name builder is used to create a custom
 * string name based on the entities name, a color
 * see {@link CustomColor} for the name, a level for the entity,
 * as well as the current and max health the entity have.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface CustomNameBuilder {

    /**
     * Specifies the name of the entity.
     *
     * @param name the name of the entity
     * @return builder to continue
     */
    CustomNameBuilder hasName(final String name);

    /**
     * Specifies the level of the entity.
     *
     * @param level the level of the entity
     * @return builder to continue
     */
    CustomNameBuilder hasLevel(final int level);

    /**
     * Specifies the maximum heath of the entity.
     *
     * @param maxHealth the initial maximum health of the entity
     * @return builder to continue
     */
    CustomNameBuilder hasMaxHealth(final int maxHealth);

    /**
     * Specifies the name color.
     * <p>
     * The custom color is used to color the
     * name. See also {@link CustomColor}
     *
     * @param customColor the name of the entity
     * @return builder to continue
     */
    CustomNameBuilder hasNameColor(final CustomColor customColor);

    /**
     * Specifies the current heath of the entity.
     *
     * @param health the current health of the entity
     * @return builder to continue
     */
    CustomNameBuilder hasCurrentHealth(final int health);

    /**
     * Creates a new string name.
     * <p>
     * Using all given information to create
     * a new custom name.
     *
     * @return new custom string name
     */
    String build();
}
