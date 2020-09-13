package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EntityInformation {


    private int level;
    private String name;
    private NameSupplier nameSupplier;
    private List<DeathDrop> customDeathDrops;

    public EntityInformation() {
        this.customDeathDrops = new ArrayList<>();
        this.nameSupplier = new CustomNameSupplier();
        this.name = "CustomEntity";
        this.level = 1;
    }

    public NameSupplier getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(@Nonnull NameSupplier nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
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

    public void setDeathDrops(@Nonnull List<DeathDrop> customDeathDrops) {
        this.customDeathDrops = customDeathDrops;
    }
}
