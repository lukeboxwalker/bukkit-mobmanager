package de.dicecraft.dicemobmanager.entity.goals;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public final class EntitySelector {

    public static final Predicate<Entity> isPlayerInSurvival = (entity) -> {
        if (EntityType.PLAYER.equals(entity.getType())) {
            GameMode gameMode = ((Player) entity).getGameMode();
            return gameMode != GameMode.SPECTATOR && gameMode != GameMode.CREATIVE;
        } else {
            return false;
        }
    };

    public static final Predicate<Entity> isProjectile = (entity) -> {
        switch (entity.getType()) {
            case FIREBALL:
            case WITHER_SKELETON:
                return true;
            default:
                return false;
        }
    };

    public static final Predicate<Entity> isSlimeEntity = (entity) -> {
        switch (entity.getType()) {
            case SLIME:
            case MAGMA_CUBE:
                return true;
            default:
                return false;
        }
    };
}
