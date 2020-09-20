package de.dicecraft.dicemobmanager.entity.goals;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public final class EntitySelector {

    public static final Predicate<Entity> IS_PLAYER_IN_SURVIVAL = (entity) -> {
        if (EntityType.PLAYER.equals(entity.getType())) {
            GameMode gameMode = ((Player) entity).getGameMode();
            return gameMode != GameMode.SPECTATOR && gameMode != GameMode.CREATIVE;
        } else {
            return false;
        }
    };

    public static final Predicate<Entity> IS_PROJECTILE = (entity) -> {
        switch (entity.getType()) {
            case FIREBALL:
            case WITHER_SKELETON:
                return true;
            default:
                return false;
        }
    };

    public static final Predicate<Entity> IS_SLIME_ENTITY = (entity) -> {
        switch (entity.getType()) {
            case SLIME:
            case MAGMA_CUBE:
                return true;
            default:
                return false;
        }
    };

    private EntitySelector() {
    }
}
