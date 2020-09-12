package de.dicecraft.dicemobmanager.entity.name;

/**
 * Providing minecraft color codes.
 * <p>
 * Basic colors 0-f can be used to color
 * string text see {@link CustomColor#color(String)}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public enum CustomColor {

    BLACK("§0"),
    DARK_BLUE("§1"),
    DARK_GREEN("§2"),
    DARK_TURQUOISE("§3"),
    DARK_RED("§4"),
    PURPLE("§5"),
    DARK_YELLOW("§6"),
    LIGHT_GREY("§7"),
    GREY("§8"),
    LIGHT_BLUE("§9"),
    LIGHT_GREEN("§a"),
    LIGHT_TURQUOISE("§b"),
    LIGHT_RED("§c"),
    MAGENTA("§d"),
    LIGHT_YELLOW("§e"),
    WHITE("§f");

    private final String colorCode;

    CustomColor(final String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Gets the color code.
     * <p>
     * Color codes have a § prefix followed
     * by a 4 bit color 0-f.
     *
     * @return the color code
     */
    public String getColorCode() {
        return colorCode;
    }

    public String color(String string) {
        return colorCode + string;
    }

    public String color(int i) {
        return colorCode + i;
    }

    public String color(double d) {
        return colorCode + d;
    }
}
