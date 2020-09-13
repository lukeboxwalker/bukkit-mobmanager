package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom information of an entity.
 * <p>
 * Contains all important information for an entity.
 * For example the level, the custom name, the name
 * supplier and the death loot.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class EntityInformation {

    private int level;
    private String name;
    private NameSupplier nameSupplier;
    private List<DeathDrop> customDeathDrops;

    /**
     * Creating new EntityInformation.
     * <p>
     * Initialize all fields with there default value
     * the entities name is set to "CustomEntity" with
     * its default level 1.
     * The default name supplier {@link CustomNameSupplier}
     * is used.
     */
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

    @Override
    public String toString() {
        return "EntityInformation{" +
                "level=" + level +
                ", name='" + name + '\'' +
                ", nameSupplier=" + nameSupplier +
                ", customDeathDrops=" + customDeathDrops +
                '}';
    }
}
