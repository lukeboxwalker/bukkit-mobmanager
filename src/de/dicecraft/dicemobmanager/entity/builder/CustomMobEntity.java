package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
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
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomMobEntity implements CustomEntity {

    private static final int HIT_RANGE = 20;
    private static final int TICKS = 3;
    private int tickTracker = 0;

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
    public CustomMobEntity() {
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

    @Override
    public void onEntityTick(Entity entity) {
        if (tickTracker < TICKS) {
            ++tickTracker;
        } else {
            World world = entity.getWorld();
            Plugin plugin = DiceMobManager.getInstance();


            if (new Random().nextInt(12) == 0 ) {
                Vector entityPos = entity.getLocation().toVector();

                List<Location> spawnLocations = new ArrayList<Location>();
                int distance = 3;

                for (int x = -distance; x <= distance; x++) {
                    for (int z = -distance; z <= distance; z++) {
                        if (Math.abs(z) > 1 || Math.abs(x) > 1) {
                            if (new Random().nextInt(2 * distance * distance) == 0) {
                                spawnLocations.add(new Location(world, entityPos.getX() + x, entityPos.getY(), entityPos.getZ() + z));
                            }
                        }
                    }
                }

                for (Iterator<Location> iterator = spawnLocations.iterator(); iterator.hasNext(); ) {
                    Location location = iterator.next();
                    int max = 5;
                    int i = 0;
                    while (i < max) {
                        i++;
                        if (Material.AIR.equals(location.getBlock().getType())) {
                            break;
                        } else {
                            location.add(0, 1, 0);
                        }
                    }
                    if (!Material.AIR.equals(location.getBlock().getType())) {
                        iterator.remove();
                    }
                }


                CustomEntity customEntity = new CustomEntity() {
                    @Override
                    public NameSupplier getNameSupplier() {
                        return CustomMobEntity.this.nameSupplier;
                    }

                    @Override
                    public String getName() {
                        return "Babyspinne";
                    }

                    @Override
                    public int getLevel() {
                        return 1;
                    }

                    @Override
                    public List<DeathDrop> getDeathDrops() {
                        return new ArrayList<>();
                    }

                    @Override
                    public boolean isAggressive() {
                        return true;
                    }
                };

                EntityBuilder builder = DiceMobManager.builder(DiceMobManager.getInstance())
                        .setType(EntityType.CAVE_SPIDER)
                        .setCustomEntity(customEntity)
                        .setAttribute(Attribute.GENERIC_MAX_HEALTH, 1);
                for (Location location : spawnLocations) {
                    try {
                        builder.setLocation(location).buildAndSpawn();
                    } catch (EntityCreationException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                List<Entity> entities = entity.getNearbyEntities(HIT_RANGE, (float) HIT_RANGE / 2, HIT_RANGE);
                for (Entity target : entities) {
                    if (target instanceof Player) {
                        Location playerLocation = target.getLocation();
                        float pitch = playerLocation.getPitch();
                        float yaw = playerLocation.getYaw();

                        Location playerPos = target.getLocation();
                        Location entityPos = entity.getLocation();

                        double x = playerPos.getX() - entityPos.getX();
                        double y = playerPos.getY() - entityPos.getY();
                        double z = playerPos.getZ() - entityPos.getZ();

                        double max = Math.max(Math.max(Math.abs(x), Math.abs(y)), Math.abs(z));

                        AtomicInteger counter = new AtomicInteger();
                        Vector position = new Vector(entityPos.getX(), entityPos.getY() - 1.5, entityPos.getZ());
                        Vector direction = new Vector(x / max / 1.5, y / max / 1.5, z / max / 1.5);

                        Location armorStandLoc = new Location(world, position.getX(), position.getY(), position.getZ(), yaw, pitch);

                        ArmorStand stand = (ArmorStand) world.spawnEntity(armorStandLoc, EntityType.ARMOR_STAND);
                        EntityEquipment equipment = stand.getEquipment();
                        if (equipment != null) {
                            equipment.setHelmet(new ItemStack(Material.COBWEB));
                        }
                        stand.setVisible(false);
                        stand.setGravity(false);

                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                if (counter.get() > HIT_RANGE) {
                                    stand.remove();
                                    cancel();
                                }
                                counter.incrementAndGet();
                                stand.teleport(stand.getLocation().clone().add(direction));

                                List<Entity> entities = stand.getNearbyEntities(0.5, 0.5, 0.5);
                                for (Entity entity : entities) {
                                    if (entity instanceof Player) {
                                        ((Player) entity).damage(1);
                                        stand.remove();
                                        cancel();
                                    }
                                }
                            }
                        }.runTaskTimer(plugin, 0, 2);
                    }
                }
            }
            tickTracker = 0;
        }
    }
}
