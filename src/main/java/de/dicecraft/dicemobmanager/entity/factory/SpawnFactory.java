package de.dicecraft.dicemobmanager.entity.factory;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.MobGoals;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.configuration.ConfigFlag;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Wither;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

public class SpawnFactory implements EntitySpawnFactory {

    private static final double DEFAULT_MAX_HEALTH = 20D;
    private static final CreatureSpawnEvent.SpawnReason SPAWN_REASON = CreatureSpawnEvent.SpawnReason.CUSTOM;

    private static final Logger LOGGER = DiceMobManager.logging();
    private static final MobGoals MOB_GOALS = Bukkit.getMobGoals();
    private static final Function<String, String> WARN_MSG = string ->
            "Trying to add a goal with goal key: " + string + " which already exist!";

    private final EntityManager manager;
    private final Configuration configuration;

    public SpawnFactory(final EntityManager manager, final Configuration configuration) {
        this.manager = manager;
        this.configuration = configuration;
    }

    @Override
    public <T extends Mob> LivingEntity spawnEntity(final ProtoEntity<T> protoEntity, final Location location) {
        final Map<Attribute, Double> attributes = protoEntity.getAttributeMap();
        attributes.putIfAbsent(Attribute.GENERIC_MAX_HEALTH, DEFAULT_MAX_HEALTH);
        final EntityType type = protoEntity.getCustomType().getEntityType();
        if (Mob.class.isAssignableFrom(Objects.requireNonNull(type.getEntityClass()))) {
            return (LivingEntity) location.getWorld().spawnEntity(location, type, SPAWN_REASON, entity -> {
                final Mob mob = (Mob) entity;
                this.prepareAttributes(mob, protoEntity, attributes);
                this.prepareCustomName(mob, protoEntity);
                this.prepareGoals(mob, protoEntity);
                this.preparePotionEffects(mob, protoEntity);

                if (mob instanceof Zombie) {
                    prepareZombie((Zombie) mob, protoEntity);
                } else if (mob instanceof Wither) {
                    prepareWither((Wither) mob);
                }

                protoEntity.getEquipment().equip(mob);
                manager.registerEntity(mob, protoEntity, configuration);
            });
        } else {
            throw new EntityCreationException("Entity type is not a mob");
        }
    }

    private void prepareWither(final Wither wither) {
        if (configuration.shouldCancel(ConfigFlag.SHOW_WITHER_BOSS_BAR)) {
            final BossBar bossBar = wither.getBossBar();
            if (bossBar != null) {
                bossBar.setVisible(false);
            }
        }
    }

    private void prepareZombie(final Zombie zombie, final ProtoEntity<? extends Mob> protoEntity) {
        zombie.setShouldBurnInDay(protoEntity.shouldBurnInDay());
    }

    private void preparePotionEffects(final Mob mob, final ProtoEntity<? extends Mob> protoEntity) {
        for (final PotionEffect potionEffect : protoEntity.getPotionEffects()) {
            mob.addPotionEffect(potionEffect);
        }
    }

    private void prepareGoals(final Mob mob, final ProtoEntity<? extends Mob> protoEntity) {
        final Set<GoalKey<Mob>> goalKeys = new HashSet<>();
        for (final PriorityEntry<GoalSupplier<Mob>> entry : protoEntity.getGoals()) {
            final Goal<Mob> goal = entry.getEntry().supply(mob);
            if (goalKeys.contains(goal.getKey())) {
                LOGGER.warning(WARN_MSG.apply(goal.getKey().toString()));
            } else {
                MOB_GOALS.addGoal(mob, entry.getPriority(), goal);
                goalKeys.add(goal.getKey());
            }
        }

        for (final GoalKey<? extends Mob> goalKey : protoEntity.getIgnoredGoals()) {
            if (goalKey.getEntityClass().isAssignableFrom(mob.getClass())) {
                removeGoal(mob, goalKey);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Mob> void removeGoal(final Mob mob, final GoalKey<T> goalKey) {
        if (goalKey.getEntityClass().isAssignableFrom(mob.getClass())) {
            MOB_GOALS.removeGoal((T) mob, goalKey);
        }
    }

    private void prepareAttributes(final Mob mob, final ProtoEntity<? extends Mob> protoEntity,
                                   final Map<Attribute, Double> attributes) {
        // disables baby zombies on chickens
        attributes.put(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS, 0D);

        protoEntity.getAttributeMap().forEach((attribute, value) -> {
            final AttributeInstance instance = mob.getAttribute(attribute);
            if (instance != null) {
                instance.setBaseValue(value);
            }
        });
        mob.setHealth(attributes.get(Attribute.GENERIC_MAX_HEALTH));
    }

    private void prepareCustomName(final Mob mob, final ProtoEntity<? extends Mob> protoEntity) {
        if (protoEntity.getName() != null && !protoEntity.getName().isEmpty()) {
            mob.setCustomNameVisible(true);
            protoEntity.getNameUpdater().updateName(mob);
        }
    }
}
