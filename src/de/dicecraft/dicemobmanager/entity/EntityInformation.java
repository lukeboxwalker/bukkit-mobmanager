package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.name.CustomNameBuilder;
import de.dicecraft.dicemobmanager.entity.name.NameBuilder;

public class EntityInformation {

    private CustomNameBuilder nameBuilder;
    private String name;
    private int level;

    public EntityInformation() {
        this.nameBuilder = new NameBuilder();
        this.name = "CustomEntity";
        this.level = 1;
    }

    public CustomNameBuilder getNameBuilder() {
        return nameBuilder;
    }

    public void setNameBuilder(CustomNameBuilder nameBuilder) {
        this.nameBuilder = nameBuilder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
