package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.GoalType;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

final class MockGoal implements CustomGoal<Mob> {

    static final GoalKey<Mob> GOAL_KEY = GoalKey.of(Mob.class, DiceMobManager.createNameSpacedKey("mock_goal"));

    @Override
    public boolean shouldActivate() {
        return false;
    }

    @Override
    @NotNull
    public GoalKey<Mob> getKey() {
        return GOAL_KEY;
    }

    @Override
    @NotNull
    public EnumSet<GoalType> getTypes() {
        return EnumSet.of(GoalType.MOVE);
    }
}
