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

public class EntityEventListener implements Listener {

    private final EntityManager manager;

    public EntityEventListener(final EntityManager manager) {
        this.manager = manager;
    }

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
                for (DeathDrop deathDrop : protoEntity.getDeathDrops()) {
                    if (deathDrop.shouldDrop(lootBonus)) {
                        Location location = entity.getLocation();
                        ItemSpawnHelper.spawnDeathDrop(entity, protoEntity, deathDrop, location);
                    }
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (manager.activateEntity(event.getEntity())) {
            Entity entity = event.getEntity();
            Optional<ProtoEntity> optional = manager.getProtoEntity(entity);
            optional.ifPresent(protoEntity -> {
                protoEntity.onEntitySpawn(new SpawnEvent((LivingEntity) entity, protoEntity, event));
                if (event.isCancelled()) {
                    manager.removeEntity(entity);
                }
            });
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (EntitySelector.IS_PROJECTILE.test(event.getEntity())) {
            final Projectile projectile = event.getEntity();
            LivingEntity shooter = (LivingEntity) projectile.getShooter();
            Optional<ProtoEntity> optional = manager.getProtoEntity(shooter);
            optional.ifPresent(protoEntity -> manager.watchProjectile(projectile, shooter));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        Optional<ProtoEntity> optional = manager.getProtoEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            protoEntity.onEntityDamage(new DamageEvent(entity, protoEntity, event));
            if (!event.isCancelled()) {
                if (protoEntity.getName() != null && !protoEntity.getName().isEmpty()) {
                    double finalHealth = (entity.getHealth() - event.getFinalDamage());
                    entity.setCustomName(protoEntity.getNameSupplier().supply(entity, finalHealth, protoEntity));
                }
            }
        });
    }
}

