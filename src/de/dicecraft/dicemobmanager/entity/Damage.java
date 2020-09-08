package de.dicecraft.dicemobmanager.entity;

import net.minecraft.server.v1_16_R2.DamageSource;
import org.bukkit.entity.Entity;

public class Damage implements CustomDamage {

    private final DamageSource damageSource;
    private Source source;
    private float damage;

    public Damage(final DamageSource damageSource, float damage) {
        this.damageSource = damageSource;
        this.damage = damage;
        for (Source source : Source.values()) {
            if (source.getNames().contains(damageSource.translationIndex)) {
                this.source = source;
                break;
            }
        }
    }

    public Source getSource() {
        return source;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public Entity getEntity() {
        net.minecraft.server.v1_16_R2.Entity entity = damageSource.getEntity();
        if (entity != null) {
            return entity.getBukkitEntity();
        } else {
            return null;
        }
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }
}
