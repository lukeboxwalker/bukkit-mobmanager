package de.dicecraft.dicemobmanager.entity.goals;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.function.Predicate;

public final class EntitySelector {

    public static final Predicate<Entity> isPlayerInSurvival = (entity) -> {
        if (entity instanceof Player) {
            GameMode gameMode = ((Player) entity).getGameMode();
            return gameMode != GameMode.SPECTATOR && gameMode != GameMode.CREATIVE;
        } else {
            return false;
        }
    };
}
