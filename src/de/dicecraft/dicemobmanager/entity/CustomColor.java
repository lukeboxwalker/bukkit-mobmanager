package de.dicecraft.dicemobmanager.entity;

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

    public String getColorCode() {
        return colorCode;
    }

    public String color(Object string) {
        return colorCode + string.toString();
    }
}
