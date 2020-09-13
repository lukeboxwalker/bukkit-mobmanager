package de.dicecraft.dicemobmanager.entity.name;

import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import org.bukkit.entity.LivingEntity;

public interface NameSupplier {

    String supply(final LivingEntity entity, final int currentHealth, final EntityInformation information);
}
