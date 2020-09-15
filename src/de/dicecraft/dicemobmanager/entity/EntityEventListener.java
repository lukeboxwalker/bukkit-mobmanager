package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.utils.Component;
import de.dicecraft.dicemobmanager.entity.builder.CustomEntity;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EntityEventListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Optional<CustomEntity> optional = DiceMobManager.getEntityManager().getCustomEntity(event.getEntity());
        optional.ifPresent(customEntity -> {
            LivingEntity entity = event.getEntity();
            customEntity.onEntityDeath(event);
            Player player = event.getEntity().getKiller();
            List<ItemStack> drops = event.getDrops();
            drops.clear();
            if (player != null) {
                Map<Enchantment, Integer> enchantments = player.getInventory().getItemInMainHand().getEnchantments();
                customEntity.getDeathDrops().forEach(deathDrop -> {
                    int lootBonus = enchantments.getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
                    if (deathDrop.shouldDrop(lootBonus)) {
                        customEntity.onItemDrop(deathDrop);
                        drops.add(deathDrop.getItemStack());

                    }
                });
            }
            DiceMobManager.getEntityManager().removeEntity(entity);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (DiceMobManager.getEntityManager().activateEntity(event.getEntity())) {
            Optional<CustomEntity> optional = DiceMobManager.getEntityManager().getCustomEntity(event.getEntity());
            optional.ifPresent(customEntity -> {
                customEntity.onEntitySpawn(event);
            });
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            Optional<CustomEntity> optional = DiceMobManager.getEntityManager().getCustomEntity(event.getEntity());
            optional.ifPresent(customEntity -> customEntity.onEntityDamage(event));
        }
    }
}
