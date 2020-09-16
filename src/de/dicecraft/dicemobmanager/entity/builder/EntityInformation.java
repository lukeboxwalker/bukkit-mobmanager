package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityInformation extends AbstractProtoEntity {

    private int level;
    private String name;
    private NameSupplier nameSupplier;
    private List<DeathDrop> deathDrops;

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
    }

    @Nonnull
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

    @Nonnull
    @Override
    public List<DeathDrop> getDeathDrops() {
        return deathDrops;
    }

    @Nonnull
    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Nonnull
    @Override
    public Map<Attribute, Double> getAttributeMap() {
        return new HashMap<>();
    }

    public void setDeathDrops(@Nonnull List<DeathDrop> deathDrops) {
        this.deathDrops = deathDrops;
    }
}
