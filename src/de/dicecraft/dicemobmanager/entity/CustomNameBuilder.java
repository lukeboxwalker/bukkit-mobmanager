package de.dicecraft.dicemobmanager.entity;

public class CustomNameBuilder implements NameBuilder {

    private String name;
    private int level;
    private int maxHealth;
    private int health;
    private CustomColor customColor;

    @Override
    public NameBuilder hasName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public NameBuilder hasNameColor(CustomColor customColor) {
        this.customColor = customColor;
        return this;
    }

    @Override
    public NameBuilder hasLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public NameBuilder hasMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    @Override
    public NameBuilder hasCurrentHealth(int health) {
        this.health = health;
        return this;
    }

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
