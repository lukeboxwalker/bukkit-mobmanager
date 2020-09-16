package de.dicecraft.dicemobmanager.entity.builder;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.entity.goals.GoalSupplier;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ProtoGoalEntity {

    /**
     * Specifies a list of goal keys which need
     * to be removed from the bukkit entity.
     *
     * @return a list of goal keys
     */
    @Nonnull
    default Set<GoalKey<Mob>> getRemovedGoals() {
        return new HashSet<>();
    }

    /**
     * Using a supplier of {@link Goal} to ensure
     * to provide a unique object for each entity when building.
     * Each goal has a priority to determine the order to use them.
     * The goal selection always prefers lower prioritised pathfinder goals.
     * The highest priority that is possible is 1.
     *
     * @return the custom goals
     */
    @Nonnull
    default List<PriorityEntry<GoalSupplier<Mob>>> getGoals() {
        return new ArrayList<>();
    }
}
