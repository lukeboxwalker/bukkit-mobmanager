package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.factory.Factory;
import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ProtoMobEntity extends AbstractProtoEntity {

    private static final int HIT_RANGE = 20;
    private static final int TICKS = 3;
    private int tickTracker = 0;

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
    public ProtoMobEntity() {
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
        return EntityType.SPIDER;
    }

    @Nonnull
    @Override
    public Map<Attribute, Double> getAttributeMap() {
        return new HashMap<>();
    }

    public void setDeathDrops(@Nonnull List<DeathDrop> deathDrops) {
        this.deathDrops = deathDrops;
    }

    @Override
    public void onEntityTick(Entity entity) {

    }
}
