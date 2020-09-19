package de.dicecraft.dicemobmanager.entity.goals;

import com.destroystokyo.paper.entity.ai.Goal;
import com.destroystokyo.paper.entity.ai.GoalKey;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.entity.Mob;

public interface CustomGoal<T extends Mob> extends Goal<T> {
    GoalKey<Mob> WALK_TO_LOCATION = GoalKey.of(Mob.class, DiceMobManager.createNameSpacedKey("walk_to_location"));
    GoalKey<Mob> AVOID_TARGET = GoalKey.of(Mob.class, DiceMobManager.createNameSpacedKey("avoid_target"));
}
