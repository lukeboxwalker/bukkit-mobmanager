package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.entity.Mob;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GoalManager {

    void ignoreGoal(GoalKey<? extends Mob> goalKey);

    void ignoreAllGoals(Collection<GoalKey<? extends Mob>> goalKeys);

    void allowGoal(GoalKey<? extends Mob> goalKey);

    void allowAllGoals(Collection<GoalKey<? extends Mob>> goalKeys);

    void changePriority(int priority, GoalKey<? extends Mob> goalKey);

    void addCustomGoal(int priority, GoalSupplier<Mob> goalSupplier);

    void addAllCustomGoal(List<PriorityEntry<GoalSupplier<Mob>>> goals);

    Set<GoalKey<? extends Mob>> getIgnoredGoals();

    List<PriorityEntry<GoalSupplier<Mob>>> getCustomGoals();
}
