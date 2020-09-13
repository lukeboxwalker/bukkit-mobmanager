package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.CustomNameBuilder;
import de.dicecraft.dicemobmanager.entity.name.NameBuilder;

import java.util.ArrayList;
import java.util.List;

public class EntityInformation {

    private CustomNameBuilder nameBuilder;
    private String name;
    private int level;
    private List<DeathDrop> customDeathDrops;

    public EntityInformation() {
        this.customDeathDrops = new ArrayList<>();
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

    public List<DeathDrop> getCustomDeathDrops() {
        return customDeathDrops;
    }

    public void setDeathDrops(List<DeathDrop> customDeathDrops) {
        this.customDeathDrops = customDeathDrops;
    }
}
