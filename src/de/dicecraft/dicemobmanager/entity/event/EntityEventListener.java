package de.dicecraft.dicemobmanager.entity.event;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.enchatment.EnchantmentHandler;
import de.dicecraft.dicemobmanager.entity.enchatment.KnockBackHandler;
import de.dicecraft.dicemobmanager.entity.enchatment.LootingHandler;
import de.dicecraft.dicemobmanager.entity.goals.EntitySelector;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public final class EntityEventListener implements Listener {

    private static final int MAX_PARTICLE = 10;
    private static final int MIN_PARTICLE = 2;
    private static final double PARTICLE_MULTIPLIER = 0.1D;
    private final Set<UUID> entityDamagedByEntity;

    private final EntityManager manager;
    private final EnchantmentHandler lootingHandler;
    private final EnchantmentHandler knockBackHandler;

    /**
     * Creates a new EntityEventListener.
     * <p>
     * Listening to events the entity manager will be effected by
     *
     * @param manager the entity manager.
     */
    public EntityEventListener(final EntityManager manager) {
        this.manager = manager;
        this.entityDamagedByEntity = new HashSet<>();
        this.lootingHandler = new LootingHandler(manager);
        this.knockBackHandler = new KnockBackHandler();
    }

    /**
     * Listens to the EntityDeathEvent to identify when a
     * custom entity died.
     * <p>
     * Whenever a custom entity dies the {@link ProtoEntity#onEntityDeath(DeathEvent, Mob)}
     * method is called to notify the entity.
     * If the entity Death isn't canceled the configured item drops will
     * spawn according to there drop chance. Each time an item Drops the entity
     * which dropped the item, will be notified by the {@link ProtoEntity#onItemDrop(ItemDropEvent, Mob)}
     * The item drop can't be canceled.
     *
     * @param event the EntityDeathEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(final EntityDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Optional<ProtoEntity<?>> optional = manager.getProtoEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            final LivingEntity entity = event.getEntity();
            callDeathEvent(protoEntity, event);
            if (!event.isCancelled()) {
                final Player player = event.getEntity().getKiller();
                event.getDrops().clear();
                lootingHandler.handle(entity, protoEntity, player);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends Mob> void callDeathEvent(final ProtoEntity<T> protoEntity, final EntityDeathEvent event) {
        protoEntity.onEntityDeath(new DeathEvent(protoEntity, event), (T) event.getEntity());
    }

    /**
     * Listens to the EntityDeathEvent to identify when a
     * custom entity spawns.
     * <p>
     * Whenever a custom entity spawns the {@link ProtoEntity#onEntitySpawn(SpawnEvent, Mob)}
     * method is called to notify the entity.
     * Activates the entity so it can be ticked by the scheduler.
     *
     * @param event the EntityDeathEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntitySpawn(final EntitySpawnEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Optional<ProtoEntity<?>> optional = manager.canActivateEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            final Entity entity = event.getEntity();
            callSpawnEvent(protoEntity, event);
            if (!event.isCancelled()) {
                manager.activateEntity(entity);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends Mob> void callSpawnEvent(final ProtoEntity<T> protoEntity, final EntitySpawnEvent event) {
        protoEntity.onEntitySpawn(new SpawnEvent(protoEntity, event), (T) event.getEntity());
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
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onProjectileLaunch(final ProjectileLaunchEvent event) {
        if (!event.isCancelled() && EntitySelector.IS_PROJECTILE.test(event.getEntity())) {
            final Projectile projectile = event.getEntity();
            final LivingEntity shooter = (LivingEntity) projectile.getShooter();
            final Optional<ProtoEntity<?>> optional = manager.getProtoEntity(shooter);
            optional.ifPresent(protoEntity -> manager.watchProjectile(projectile, shooter));
        }
    }


    /**
     * Listens to the EntityDamageEvent to identify when a
     * custom entity takes damage.
     * <p>
     * Whenever a custom entity takes damage the {@link ProtoEntity#onEntityDamage(DamageEvent, Mob)}
     * method is called to notify the entity.
     * Updates the custom name of the entity if the name is not null and not empty.
     *
     * @param event the EntityDamageEvent
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(final EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Optional<ProtoEntity<?>> optional = manager.getProtoEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            callDamageEvent(protoEntity, event);
            if (!event.isCancelled() && protoEntity.getName() != null && !protoEntity.getName().isEmpty()) {
                final double finalHealth = entity.getHealth() - event.getFinalDamage();
                protoEntity.getNameUpdater().updateName(entity, finalHealth);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private <T extends Mob> void callDamageEvent(final ProtoEntity<T> protoEntity, final EntityDamageEvent event) {
        protoEntity.onEntityDamage(new DamageEvent(protoEntity, event), (T) event.getEntity());
    }

    /**
     * Listens to the EntityDamageByEntityEvent to identify when a
     * custom entity takes damage by a player.
     * <p>
     * The event fakes the actual hit on the entity due to wrong particle
     * calculation when dealing with hit damage values.
     *
     * @param event the EntityDamageByEntityEvent event.
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (entityDamagedByEntity.remove(event.getEntity().getUniqueId())) {
            return;
        } else {
            entityDamagedByEntity.add(event.getEntity().getUniqueId());
        }
        final Optional<ProtoEntity<?>> optional = manager.getProtoEntity(event.getEntity());
        optional.ifPresent(protoEntity -> {
            if (EntityType.PLAYER.equals(event.getDamager().getType())) {
                final Player player = (Player) event.getDamager();
                final double finalDamage = event.getFinalDamage();
                event.setCancelled(true);
                final LivingEntity entity = (LivingEntity) event.getEntity();
                entity.damage(finalDamage, player);
                final Location loc = entity.getLocation();
                final int particleCount = DiceMobManager.randomIntBetween(MIN_PARTICLE, MAX_PARTICLE);
                entity.getWorld().spawnParticle(Particle.DAMAGE_INDICATOR, loc.getX(), loc.getY(), loc.getZ(),
                        particleCount, 0D, 0D, 0D, PARTICLE_MULTIPLIER);

                knockBackHandler.handle(entity, protoEntity, player);
            }
        });
    }

}

