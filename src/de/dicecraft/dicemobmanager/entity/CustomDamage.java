package de.dicecraft.dicemobmanager.entity;

import org.bukkit.entity.Entity;

public interface CustomDamage {

    Entity getEntity();

    float getDamage();

    void setDamage(float damage);

    Source getSource();


}
