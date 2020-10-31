package de.dicecraft.dicemobmanager.entity.strategy;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;

public interface Strategy<T extends Mob> extends RegistrationAcceptor {

    @Nonnull
    NamespacedKey getKey();

}
