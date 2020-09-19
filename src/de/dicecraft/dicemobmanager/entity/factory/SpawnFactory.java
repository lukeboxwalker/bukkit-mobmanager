package de.dicecraft.dicemobmanager.entity.factory;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.MobGoals;
import com.destroystokyo.paper.entity.ai.PaperMobGoals;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.event.Event;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import it.unimi.dsi.fastutil.Function;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class SpawnFactory implements EntitySpawnFactory {

    private static final Logger LOGGER = DiceMobManager.logger();
    private static final MobGoals MOB_GOALS = new PaperMobGoals();
    private static final Function<String, String> WARN_MSG = string ->
            "Trying to add a goal with goal key: " + string + " which already exist!";

    private final EntityManager manager;
    private final Configuration configuration;

    public SpawnFactory(final EntityManager manager, final Configuration configuration) {
        this.manager = manager;
        this.configuration = configuration;
    }

    @Override
    public LivingEntity spawnEntity(ProtoEntity protoEntity, Location spawnLocation) {
        return spawnEntity(protoEntity, spawnLocation, (entity) -> {});
    }

    @Override
    public LivingEntity spawnEntity(ProtoEntity protoEntity, Location spawnLocation, Consumer<Entity> consumer) {
        final Map<Attribute, Double> attributes = protoEntity.getAttributeMap();
        attributes.putIfAbsent(Attribute.GENERIC_MAX_HEALTH, 20D);
        final EntityType type = protoEntity.getEntityType();
        if (Mob.class.isAssignableFrom(Objects.requireNonNull(type.getEntityClass()))) {
            return (LivingEntity) spawnLocation.getWorld().spawnEntity(spawnLocation, type, CreatureSpawnEvent.SpawnReason.CUSTOM, entity -> {
                Mob mob = (Mob) entity;
                protoEntity.getAttributeMap().forEach((attribute, value) -> {
                    AttributeInstance instance = mob.getAttribute(attribute);
                    if (instance != null) {
                        instance.setBaseValue(value);
                    }
                });
                mob.setHealth(attributes.get(Attribute.GENERIC_MAX_HEALTH));
                if (protoEntity.getName() != null && !protoEntity.getName().isEmpty()) {
                    entity.setCustomNameVisible(true);
                    entity.setCustomName(protoEntity.getNameSupplier().supply(mob, mob.getHealth(), protoEntity));
                }

                final Set<GoalKey<Mob>> goalKeys = new HashSet<>();
                for (PriorityEntry<GoalSupplier<Mob>> entry : protoEntity.getGoals()) {
                    final Goal<Mob> goal = entry.getEntry().supply((Mob) entity);
                    if (!goalKeys.contains(goal.getKey())) {
                        MOB_GOALS.addGoal((Mob) entity, entry.getPriority(), goal);
                        goalKeys.add(goal.getKey());
                    } else {
                        LOGGER.warning(WARN_MSG.apply(goal.getKey().toString()));
                    }
                }

                for (GoalKey<Mob> goalKey : protoEntity.getIgnoredGoals()) {
                    MOB_GOALS.removeGoal(mob, goalKey);
                }

                for (PotionEffect potionEffect : protoEntity.getPotionEffects()) {
                    mob.addPotionEffect(potionEffect);
                }

                if (entity instanceof Zombie) {
                    ((Zombie) entity).setShouldBurnInDay(false);
                }

                protoEntity.getEquipment().equip(mob);
                manager.registerEntity(mob, protoEntity);
            });
        } else {
            throw new EntityCreationException("Entity type is not a mob");
        }
    }

    @Override
    public Item spawnDeathDrop(LivingEntity entity, ProtoEntity protoEntity, DeathDrop deathDrop, Location location) {
        Item item = location.getWorld().dropItemNaturally(location, deathDrop.getItemStack().clone());
        protoEntity.onItemDrop(new ItemDropEvent(entity, protoEntity, deathDrop, item));
        return item;
    }

    @Override
    public Item spawnDeathDrop(Event event, DeathDrop deathDrop, Location location) {
        Item item = location.getWorld().dropItemNaturally(location, deathDrop.getItemStack().clone());
        event.getProtoEntity().onItemDrop(new ItemDropEvent(event, deathDrop, item));
        return item;
    }
}
