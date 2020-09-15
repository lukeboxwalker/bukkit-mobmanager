package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class EntityInformation implements CustomEntity {

    private int level;
    private String name;
    private NameSupplier nameSupplier;
    private List<DeathDrop> deathDrops;
    private boolean aggressive;

    /**
     * Creating new CustomMobEntity.
     * <p>
     * Initialize all fields with there default value
     * the entities name is set to "CustomEntity" with
     * its default level 1.
     * The default name supplier {@link CustomNameSupplier}
     * is used.
     */
    public EntityInformation() {
        this.deathDrops = new ArrayList<>();
        this.nameSupplier = new CustomNameSupplier();
        this.name = "CustomEntity";
        this.level = 1;
        this.aggressive = true;
    }

    @Override
    public NameSupplier getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(@Nonnull NameSupplier nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(@Nonnull String name) {
        this.name = name;
    }

    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public List<DeathDrop> getDeathDrops() {
        return deathDrops;
    }

    public void setDeathDrops(@Nonnull List<DeathDrop> deathDrops) {
        this.deathDrops = deathDrops;
    }

    @Override
    public boolean isAggressive() {
        return aggressive;
    }

    public void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }
}
