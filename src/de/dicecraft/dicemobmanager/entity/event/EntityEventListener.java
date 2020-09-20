package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.factory.ItemSpawnHelper;
import de.dicecraft.dicemobmanager.entity.goals.EntitySelector;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class EntityEventListener implements Listener {

    private final EntityManager manager;

    public EntityEventListener(final EntityManager manager) {
        this.manager = manager;
    }

    /**
     * Listens to the EntityDeathEvent to identify when a
     * custom entity died.
     * <p>
     * Whenever a custom entity dies the {@link ProtoEntity#onEntityDeath(DeathEvent)}
     * method is called to notify the entity.
     * If the entity Death isn't canceled the configured item drops will
     * spawn according to there drop chance. Each time an item Drops the entity
     * which dropped the item, will be notified by the {@link ProtoEntity#onItemDrop(ItemDropEvent)}
     * The item drop can't be canceled.
     *
     * @param event the EntityDeathEvent
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Optional<ProtoEntity> optional = manager.getProtoEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            LivingEntity entity = event.getEntity();
            protoEntity.onEntityDeath(new DeathEvent(entity, protoEntity, event));
            if (!event.isCancelled()) {
                Player player = event.getEntity().getKiller();
                List<ItemStack> drops = event.getDrops();
                drops.clear();
                int lootBonus = 0;
                if (player != null) {
                    Map<Enchantment, Integer> enchantments = player.getInventory()
                            .getItemInMainHand().getEnchantments();
                    lootBonus = enchantments.getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
                }
                if (manager.canItemsDrop()) {
                    for (DeathDrop deathDrop : protoEntity.getDeathDrops()) {
                        if (deathDrop.shouldDrop(lootBonus)) {
                            Location location = entity.getLocation();
                            ItemSpawnHelper.spawnDeathDrop(entity, protoEntity, deathDrop, location);
                        }
                    }
                }
            }
        });
    }

    /**
     * Listens to the EntityDeathEvent to identify when a
     * custom entity spawns.
     * <p>
     * Whenever a custom entity spawns the {@link ProtoEntity#onEntitySpawn(SpawnEvent)}
     * method is called to notify the entity.
     * Activates the entity so it can be ticked by the scheduler.
     *
     * @param event the EntityDeathEvent
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {
        Optional<ProtoEntity> optional = manager.canActivateEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            Entity entity = event.getEntity();
            protoEntity.onEntitySpawn(new SpawnEvent((LivingEntity) entity, protoEntity, event));
            if (!event.isCancelled()) {
                manager.activateEntity(entity);
            }
        });
    }

    /**
     * Listens to the ProjectileLaunchEvent to identify when a
     * custom entity launches a projectile.
     * <p>
     * The projectiles launched by a custom entity will be added to the active
     * {@link EntityManager} to track when ever they hit a target or do damage.
     *
     * @param event the ProjectileLaunchEvent
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (EntitySelector.IS_PROJECTILE.test(event.getEntity())) {
            final Projectile projectile = event.getEntity();
            LivingEntity shooter = (LivingEntity) projectile.getShooter();
            Optional<ProtoEntity> optional = manager.getProtoEntity(shooter);
            optional.ifPresent(protoEntity -> manager.watchProjectile(projectile, shooter));
        }
    }

    /**
     * Listens to the EntityDamageEvent to identify when a
     * custom entity takes damage.
     * <p>
     * Whenever a custom entity takes damage the {@link ProtoEntity#onEntityDamage(DamageEvent)}
     * method is called to notify the entity.
     * Updates the custom name of the entity if the name is not null and not empty.
     *
     * @param event the EntityDamageEvent
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        Optional<ProtoEntity> optional = manager.getProtoEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            protoEntity.onEntityDamage(new DamageEvent(entity, protoEntity, event));
            if (!event.isCancelled()) {
                if (protoEntity.getName() != null && !protoEntity.getName().isEmpty()) {
                    double finalHealth = (entity.getHealth() - event.getFinalDamage());
                    protoEntity.getNameUpdater().updateName(entity, finalHealth);
                }
            }
        });
    }
}

