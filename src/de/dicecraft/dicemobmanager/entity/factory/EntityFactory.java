package de.dicecraft.dicemobmanager.entity.factory;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.MobGoals;
import com.destroystokyo.paper.entity.ai.PaperMobGoals;
import de.dicecraft.dicemobmanager.entity.CustomEntities;
import de.dicecraft.dicemobmanager.entity.EntityInformation;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import de.dicecraft.dicemobmanager.entity.builder.PriorityEntry;
import de.dicecraft.dicemobmanager.entity.builder.EntityConfiguration;
import de.dicecraft.dicemobmanager.entity.drops.CustomDeathDrop;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import net.minecraft.server.v1_16_R2.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.craftbukkit.v1_16_R2.attribute.CraftAttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Creates and spawns Entity.
 * <p>
 * For Entity configuration also see {@link EntityConfiguration}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class EntityFactory implements CustomEntityFactory {

    /**
     * Spawns an Entity at a given location.
     * <p>
     * Entity is spawned by using the {@link World} given inside of
     * the location. Entity is always spawned with {@link CreatureSpawnEvent.SpawnReason#CUSTOM}.
     *
     * @param configuration to configure the entity
     * @param plugin        the plugin that spawns the entity
     * @throws EntityCreationException when entity creation fails.
     */
    @Override
    public Entity spawnEntity(EntityConfiguration configuration, final Plugin plugin) throws EntityCreationException {
        final MobGoals mobGoals = new PaperMobGoals();
        final Location location = configuration.getLocation();
        if (Mob.class.isAssignableFrom(Objects.requireNonNull(configuration.getEntityType().getEntityClass()))) {
            return location.getWorld().spawnEntity(location, configuration.getEntityType(), CreatureSpawnEvent.SpawnReason.CUSTOM, entity -> {
                Mob mob = (Mob) entity;
                Map<Attribute, Double> attributeMap = configuration.getAttributes();
                attributeMap.forEach((attribute, value) -> {
                    AttributeInstance instance = mob.getAttribute(attribute);
                    if (instance != null) {
                        instance.setBaseValue(value);
                    }
                });
                mob.setHealth(configuration.getAttributes().get(Attribute.GENERIC_MAX_HEALTH));
                entity.setCustomNameVisible(true);
                for (PriorityEntry<Function<Mob, Goal<Mob>>> entry : configuration.getPathfinderGoals()) {
                    mobGoals.addGoal((Mob) entity, entry.getPriority(), entry.getEntry().apply((Mob) entity));
                }
                if (entity instanceof Zombie) {
                    ((Zombie) entity).setShouldBurnInDay(false);
                }
                DeathDrop deathDrop = new CustomDeathDrop(new ItemStack(Material.DIAMOND), 1, DeathDrop.Rarity.LEGENDARY);

                EntityInformation entityInformation = new EntityInformation();
                entityInformation.setDeathDrops(Collections.singletonList(deathDrop));

                CustomEntities.addEntity(mob, entityInformation, plugin);
            });
        } else {
            throw new EntityCreationException("Entity type is not a mob");
        }
    }
}
