package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.GoalKey;

import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.entity.Mob;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MobGoalManager implements GoalManager {

    private final Set<GoalKey<? extends Mob>> ignoredGoals;
    private final List<PriorityEntry<GoalSupplier<Mob>>> pathfinderGoals;

    public MobGoalManager() {
        this.ignoredGoals = new HashSet<>();
        this.pathfinderGoals = new ArrayList<>();
    }

    @Override
    public void ignoreGoal(GoalKey<? extends Mob> goalKey) {
        ignoredGoals.add(goalKey);
    }

    @Override
    public void ignoreAllGoals(Collection<GoalKey<? extends Mob>> goalKeys) {
        ignoredGoals.addAll(goalKeys);
    }

    @Override
    public void allowGoal(GoalKey<? extends Mob> goalKey) {
        ignoredGoals.remove(goalKey);
    }

    @Override
    public void allowAllGoals(Collection<GoalKey<? extends Mob>> goalKeys) {
        ignoredGoals.removeAll(goalKeys);
    }

    @Override
    public void addCustomGoal(int priority, GoalSupplier<Mob> goalSupplier) {
        pathfinderGoals.add(new PriorityEntry<>(priority, goalSupplier));
    }

    @Override
    public void addAllCustomGoal(List<PriorityEntry<GoalSupplier<Mob>>> goals) {
        pathfinderGoals.addAll(goals);
    }

    @Override
    public Set<GoalKey<? extends Mob>> getIgnoredGoals() {
        return Collections.unmodifiableSet(ignoredGoals);
    }

    @Override
    public List<PriorityEntry<GoalSupplier<Mob>>> getCustomGoals() {
        return Collections.unmodifiableList(pathfinderGoals);
    }
}
