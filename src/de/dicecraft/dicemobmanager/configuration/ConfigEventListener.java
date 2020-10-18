package de.dicecraft.dicemobmanager.configuration;

import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.goals.EntitySelector;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.Optional;

/**
 * Responsible to cancel events which should
 * be canceled due to the configuration of the entity.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class ConfigEventListener implements Listener {

    private final EntityManager manager;

    public ConfigEventListener(final EntityManager manager) {
        this.manager = manager;
    }

    /**
     * Listening to SlimeSplitEvent.
     * <p>
     * Cancels the event if SLIME_SPLIT flag is
     * false
     *
     * @param event the slime split event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSlimeSplit(SlimeSplitEvent event) {
        if (!event.isCancelled()) {
            Optional<Configuration> optional = manager.getEntityConfig(event.getEntity());
            optional.ifPresent(configuration -> {
                manager.removeEntity(event.getEntity());
                event.setCancelled(configuration.shouldCancel(ConfigFlag.SLIME_SPLIT));
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityMount(EntityMountEvent event) {
        if (!event.isCancelled()) {
            if (event.getEntity().getType() == EntityType.ZOMBIE && event.getMount().getType() == EntityType.CHICKEN) {
                Optional<Configuration> optional = manager.getEntityConfig(event.getEntity());
                if (optional.isPresent() && !manager.getProtoEntity(event.getMount()).isPresent()) {
                    if (optional.get().shouldCancel(ConfigFlag.SPAWN_NATURAL_CHICKEN_JOCKEY)) {
                        event.setCancelled(true);
                        event.getMount().remove();
                    }
                }
            }
        }
    }

    /**
     * Listening to ExplosionPrimeEvent.
     * <p>
     * Cancels the event if CREEPER_EXPLOSION_DAMAGE flag is
     * false, or the event was fired by a projectile and the
     * PROJECTILE_BLOCK_DAMAGE flag is false
     *
     * @param event the explosion event
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplodeDamage(ExplosionPrimeEvent event) {
        if (!event.isCancelled()) {
            Optional<Configuration> optional = manager.getEntityConfig(event.getEntity());
            optional.ifPresent(configuration -> {
                if (configuration.shouldCancel(ConfigFlag.CREEPER_EXPLOSION_DAMAGE)) {
                    Entity entity = event.getEntity();
                    entity.getWorld().createExplosion(entity.getLocation(), event.getRadius(), false, false);
                    event.setCancelled(true);
                }
            });
            if (EntitySelector.IS_PROJECTILE.test(event.getEntity())) {
                Projectile projectile = (Projectile) event.getEntity();
                if (manager.isWatchingProjectile(projectile)) {
                    optional = manager.getEntityConfig(manager.getProjectileShooter(projectile));
                    optional.ifPresent(configuration -> {
                        manager.unWatchProjectile(projectile);
                        if (configuration.shouldCancel(ConfigFlag.PROJECTILE_BLOCK_DAMAGE)) {
                            Entity entity = event.getEntity();
                            entity.getWorld().createExplosion(entity.getLocation(), event.getRadius(), false, false);
                            event.setCancelled(true);
                        }
                    });
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onSheepShear(PlayerShearEntityEvent event) {
        if (!event.isCancelled()) {
            Optional<Configuration> optional = manager.getEntityConfig(event.getEntity());
            optional.ifPresent(configuration -> {
                event.setCancelled(configuration.shouldCancel(ConfigFlag.CAN_SHEAR_SHEEP));
            });
        }
    }

}

