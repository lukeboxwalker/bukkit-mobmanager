package de.dicecraft.dicemobmanager;

import com.destroystokyo.paper.entity.ai.MobGoals;
import com.destroystokyo.paper.entity.ai.PaperMobGoals;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SpawnManager implements Listener {

    private static final Set<UUID> ENTITIES = new HashSet<>();
    private static final Map<UUID, LastAttacker> LAST_ATTACKER = new HashMap<>();

    public void spawn(final Location location) {
        final MobGoals mobGoals = new PaperMobGoals();
        Entity spawnedEntity = location.getWorld().spawnEntity(location, EntityType.ZOMBIE, CreatureSpawnEvent.SpawnReason.CUSTOM, entity -> {
            Zombie zombie = (Zombie) entity;
            mobGoals.addGoal(zombie, 1, new PathfinderWalk(zombie, location.clone().add(10, 0, 10)));
        });

        ENTITIES.add(spawnedEntity.getUniqueId());
    }

    @EventHandler
    public void damageEvent(EntityDamageByEntityEvent damageEvent) {
//        Entity entity = damageEvent.getEntity();
//        Entity attacker = damageEvent.getDamager();
//        if (ENTITIES.contains(entity.getUniqueId()) && attacker instanceof LivingEntity) {
//            LAST_ATTACKER.put(entity.getUniqueId(), new LastAttacker((LivingEntity) attacker));
//        }
    }


    @EventHandler
    public void combustEvent(EntityCombustEvent combustEvent) {
//        Entity entity = combustEvent.getEntity();
//        if (entity.getLastDamageCause() != null) {
//            System.out.println(entity.getLastDamageCause().getCause());
//        }
//        if (ENTITIES.contains(entity.getUniqueId())) {
//            if (entity.getLastDamageCause() != null && !entity.getLastDamageCause().getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) {
//                combustEvent.setCancelled(true);
//                entity.setFireTicks(-1);
//            }
//            if (LAST_ATTACKER.containsKey(entity.getUniqueId())) {
//                if (!LAST_ATTACKER.get(entity.getUniqueId()).getEnchantments().containsKey(Enchantment.FIRE_ASPECT)) {
//                    combustEvent.setCancelled(true);
//                    entity.setFireTicks(-1);
//                }
//            } else {
//                combustEvent.setCancelled(true);
//                entity.setFireTicks(-1);
//            }
//
//        }


    }
}
