package de.dicecraft.dicemobmanager.entity.factory;

import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.MobGoals;
import com.destroystokyo.paper.entity.ai.PaperMobGoals;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.event.Event;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
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

import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class SpawnFactory implements EntitySpawnFactory {

    private final MobGoals mobGoals = new PaperMobGoals();

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

                for (PriorityEntry<GoalSupplier<Mob>> entry : protoEntity.getGoals()) {
                    mobGoals.addGoal((Mob) entity, entry.getPriority(), entry.getEntry().supply((Mob) entity));
                }

                for (GoalKey<Mob> goalKey : protoEntity.getRemovedGoals()) {
                    mobGoals.removeGoal(mob, goalKey);
                }

                for (PotionEffect potionEffect : protoEntity.getPotionEffects()) {
                    mob.addPotionEffect(potionEffect);
                }

                if (entity instanceof Zombie) {
                    ((Zombie) entity).setShouldBurnInDay(false);
                }

                protoEntity.getEquipment().equip(mob);
                DiceMobManager.getEntityManager().addEntity(mob, protoEntity);
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
