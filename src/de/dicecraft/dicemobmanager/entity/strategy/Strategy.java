package de.dicecraft.dicemobmanager.entity.strategy;

import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

public interface Strategy extends RegistrationAcceptor {

    @Nonnull
    NamespacedKey getKey();
}
