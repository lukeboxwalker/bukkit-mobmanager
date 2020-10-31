package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.Goal;
import org.bukkit.entity.Mob;

@FunctionalInterface
public interface GoalSupplier<T extends Mob> {

    /**
     * Provides a new goal for the entity.
     *
     * @param mob to create goal for
     * @return the custom goal
     */
    Goal<T> supply(Mob mob);
}
