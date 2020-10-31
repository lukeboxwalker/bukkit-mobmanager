package de.dicecraft.dicemobmanager.entity.enchatment;

import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;

public class KnockBackHandler implements EnchantmentHandler {

    private static final double DEFAULT_STRENGTH = 0.4D;
    private static final double DEG_TO_RAD = Math.PI / 180.0D;
    private static final double KNOCK_BACK_MULTIPLIER = 0.5D;

    @Override
    public void handle(final LivingEntity attacked, final ProtoEntity<?> attackedProtoEntity, final Player attacker) {
        final Map<Enchantment, Integer> enchantments = attacker.getInventory()
                .getItemInMainHand().getEnchantments();
        final double knockBack = enchantments.getOrDefault(Enchantment.KNOCKBACK, 0);

        final Location playerLoc = attacker.getLocation();
        doKnockBack(knockBack, playerLoc.getYaw(), attacked);
    }

    private void doKnockBack(final double knockBack, final float yaw, final LivingEntity livingEntity) {
        double actualKnockBack = Math.max(knockBack * KNOCK_BACK_MULTIPLIER, DEFAULT_STRENGTH);
        final AttributeInstance instance = livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        if (instance != null) {
            actualKnockBack = actualKnockBack * (1.0D - instance.getBaseValue());
        }
        if (actualKnockBack > 0) {
            final Vector velocity = livingEntity.getVelocity();
            final Vector direction = new Vector(-Math.sin(yaw * DEG_TO_RAD), 0.0D, Math.cos(yaw * DEG_TO_RAD));
            direction.normalize().multiply(actualKnockBack);

            final double posX = velocity.getX() / 2.0D + direction.getX();
            final double minY = Math.min(DEFAULT_STRENGTH, velocity.getY() / 2.0D + actualKnockBack);
            final double posY = livingEntity.isOnGround() ? minY : velocity.getY();
            final double posZ = velocity.getZ() / 2.0D + direction.getZ();
            livingEntity.setVelocity(new Vector(posX, posY, posZ));
        }

    }
}
