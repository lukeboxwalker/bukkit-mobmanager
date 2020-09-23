package de.dicecraft.dicemobmanager.entity.enchatment;

import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
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

    @Override
    public void handle(LivingEntity attacked, ProtoEntity attackedProtoEntity, Player attacker) {
        Map<Enchantment, Integer> enchantments = attacker.getInventory()
                .getItemInMainHand().getEnchantments();
        double knockBack = enchantments.getOrDefault(Enchantment.KNOCKBACK, 0);

        Location playerLoc = attacker.getLocation();
        doKnockBack(knockBack, playerLoc.getYaw(), attacked);
    }

    private void doKnockBack(double knockBack, float yaw, LivingEntity livingEntity) {
        knockBack = Math.max(knockBack, DEFAULT_STRENGTH);
        AttributeInstance instance = livingEntity.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE);
        if (instance != null) {
            knockBack = knockBack * (1.0D - instance.getBaseValue());
        }
        if (knockBack > 0.0D) {
            Vector velocity = livingEntity.getVelocity();
            Vector direction = new Vector(-Math.sin(yaw * DEG_TO_RAD), 0.0D, Math.cos(yaw * DEG_TO_RAD));
            direction.normalize().multiply(knockBack);

            double x = velocity.getX() / 2.0D + direction.getX();
            double minY = Math.min(DEFAULT_STRENGTH, velocity.getY() / 2.0D + knockBack);
            double y = livingEntity.isOnGround() ? minY : velocity.getY();
            double z = velocity.getZ() / 2.0D + direction.getZ();
            livingEntity.setVelocity(new Vector(x, y, z));
        }

    }
}
