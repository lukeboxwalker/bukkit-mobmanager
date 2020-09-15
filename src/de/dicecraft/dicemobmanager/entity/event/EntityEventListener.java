package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.utils.Component;
import de.dicecraft.dicemobmanager.entity.builder.CustomEntity;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EntityEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Optional<Component<Plugin, CustomEntity>> optional = DiceMobManager.getEntityManager().getInformation(event.getEntity());
        optional.ifPresent(pair -> {
            CustomEntityDeathEvent spawnEvent = new CustomEntityDeathEvent(event, pair);
            DiceMobManager.getEventManager().callEvent(spawnEvent);
            if (!spawnEvent.isCancelled()) {
                LivingEntity entity = event.getEntity();
                CustomEntity information = pair.getSecond();
                DiceMobManager.getEntityManager().removeEntity(entity);
                Player player = event.getEntity().getKiller();
                List<ItemStack> drops = event.getDrops();
                drops.clear();
                if (player != null) {
                    Map<Enchantment, Integer> enchantments =  player.getInventory().getItemInMainHand().getEnchantments();
                    information.getDeathDrops().forEach(deathDrop -> {
                        int lootBonus = enchantments.getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
                        if (deathDrop.shouldDrop(lootBonus)) {
                            CustomEntityDropItemEvent dropItemEvent = new CustomEntityDropItemEvent(entity, deathDrop, pair);
                            DiceMobManager.getEventManager().callEvent(dropItemEvent);
                            if (!dropItemEvent.isCancelled()) {
                                drops.add(deathDrop.getItemStack());
                            }
                        }
                    });
                }
            }
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity && DiceMobManager.getEntityManager().activateEntity(event.getEntity())) {
            Optional<Component<Plugin, CustomEntity>> optional = DiceMobManager.getEntityManager().getInformation(event.getEntity());
            optional.ifPresent(pair -> {
                CustomEntitySpawnEvent spawnEvent = new CustomEntitySpawnEvent(event, pair);
                DiceMobManager.getEventManager().callEvent(spawnEvent);
            });
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            Optional<Component<Plugin, CustomEntity>> optional = DiceMobManager.getEntityManager().getInformation(event.getEntity());
            optional.ifPresent(pair -> {
                CustomEntityDamageEvent spawnEvent = new CustomEntityDamageEvent(event, pair);
                DiceMobManager.getEventManager().callEvent(spawnEvent);
            });
        }
    }
}
