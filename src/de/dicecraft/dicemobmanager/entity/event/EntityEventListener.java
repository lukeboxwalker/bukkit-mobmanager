package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.factory.EntitySpawnFactory;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EntityEventListener implements Listener {

    private final EntitySpawnFactory factory;
    private final EntityManager manager;

    public EntityEventListener(final EntitySpawnFactory factory, final EntityManager manager) {
        this.factory = factory;
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
                if (player != null) {
                    Map<Enchantment, Integer> enchantments = player.getInventory().getItemInMainHand().getEnchantments();
                    protoEntity.getDeathDrops().forEach(deathDrop -> {
                        int lootBonus = enchantments.getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
                        if (deathDrop.shouldDrop(lootBonus)) {
                            Location location = entity.getLocation();
                            factory.spawnDeathDrop(entity, protoEntity, deathDrop, location);
                        }
                    });
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            if (manager.activateEntity(entity)) {
                Optional<ProtoEntity> optional = manager.getProtoEntity(entity);
                optional.ifPresent(protoEntity -> {
                    protoEntity.onEntitySpawn(new SpawnEvent(entity, protoEntity, event));
                    if (event.isCancelled()) {
                        manager.removeEntity(entity);
                    }
                });
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            Optional<ProtoEntity> optional = manager.getProtoEntity(entity);
            optional.ifPresent(protoEntity -> {
                protoEntity.onEntityDamage(new DamageEvent(entity, protoEntity, event));
                if (!event.isCancelled()) {
                    double finalHealth = (entity.getHealth() - event.getFinalDamage());
                    entity.setCustomName(protoEntity.getNameSupplier().supply(entity, finalHealth, protoEntity));
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSlimeSplit(SlimeSplitEvent event) {
        Optional<ProtoEntity> optional = manager.getProtoEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            manager.removeEntity(event.getEntity());
            protoEntity.onSlimeSplit(new SlimeEvent(event.getEntity(), protoEntity, event));
        });
    }
}
