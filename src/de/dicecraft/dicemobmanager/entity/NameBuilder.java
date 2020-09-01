package de.dicecraft.dicemobmanager.entity;

public interface NameBuilder {

    NameBuilder hasName(final String name);

    NameBuilder hasLevel(final int level);

    NameBuilder hasMaxHealth(final int heath);

    NameBuilder hasNameColor(CustomColor customColor);

    NameBuilder hasCurrentHealth(final int heath);

    String build();

}
