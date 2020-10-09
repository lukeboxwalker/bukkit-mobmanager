package de.dicecraft.dicemobmanager.entity.builder;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;

public final class CustomType<T extends Mob> {

    public static final CustomType<Zombie> ZOMBIE = new CustomType<>(EntityType.ZOMBIE);
    public static final CustomType<Skeleton> SKELETON = new CustomType<>(EntityType.SKELETON);

    private final EntityType entityType;

    private CustomType(final EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
