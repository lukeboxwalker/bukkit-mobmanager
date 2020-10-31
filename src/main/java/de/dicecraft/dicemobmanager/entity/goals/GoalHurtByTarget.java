package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import de.dicecraft.dicemobmanager.DiceMobManager;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class GoalHurtByTarget extends TargetGoal<Mob> implements Listener {

    private final Set<Class<? extends LivingEntity>> attackedClasses;

    private long lastHit;
    private long lastActivation;

    /**
     * Creates a new GoalHurtByTarget.
     * <p>
     * Selecting the target that hit the mob which uses this goal.
     *
     * @param mob             the mob that uses the goal.
     * @param attackedClasses the entity class types the goal is targeting.
     */
    @SafeVarargs
    public GoalHurtByTarget(final Mob mob, final Class<? extends LivingEntity>... attackedClasses) {
        super(mob);
        this.lastHit = System.currentTimeMillis();
        this.lastActivation = System.currentTimeMillis();
        this.attackedClasses = new HashSet<>(Arrays.asList(attackedClasses));
        Bukkit.getPluginManager().registerEvents(this, DiceMobManager.getInstance());
    }

    /**
     * Listens on EntityDamageByEntityEvent event.
     * <p>
     * Used to reset the lastHit time.
     *
     * @param event the EntityDamageByEntityEvent
     */
    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getEntity().equals(mob)) {
            this.lastHit = System.currentTimeMillis();
        }
    }

    /**
     * Listens on EntityDeath event.
     * <p>
     * Used to unregister this listener.
     *
     * @param event the EntityDeathEvent
     */
    @EventHandler
    public void onEntityDeath(final EntityDeathEvent event) {
        if (!event.isCancelled() && event.getEntity().equals(mob)) {
            HandlerList.unregisterAll(this);
        }
    }

    @Override
    public boolean shouldActivate() {
        final EntityDamageEvent entityDamageEvent = this.mob.getLastDamageCause();
        if (lastHit != lastActivation && entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final Entity attacker = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            if (attacker instanceof LivingEntity) {
                final Boolean gameRule = mob.getWorld().getGameRuleValue(GameRule.UNIVERSAL_ANGER);
                if (attacker.getType() == EntityType.PLAYER && gameRule != null && gameRule) {
                    return false;
                } else {
                    boolean isAttackAble = false;
                    for (final Class<? extends LivingEntity> clazz : attackedClasses) {
                        if (clazz.isAssignableFrom(attacker.getClass())) {
                            isAttackAble = true;
                        }
                    }
                    return isAttackAble;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        final EntityDamageEvent entityDamageEvent = this.mob.getLastDamageCause();
        if (entityDamageEvent instanceof EntityDamageByEntityEvent) {
            final Entity attacker = ((EntityDamageByEntityEvent) entityDamageEvent).getDamager();
            if (attacker instanceof LivingEntity) {
                mob.setTarget((LivingEntity) attacker);
                setTarget(mob.getTarget());
                this.lastActivation = this.lastHit;
                super.start();
            }
        }
    }

    @Nonnull
    @Override
    public GoalKey<Mob> getKey() {
        return CustomGoal.HURT_BY_TARGET;
    }

    @Nonnull
    @Override
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.TARGET);
    }
}
