package de.dicecraft.dicemobmanager.entity.goals;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import com.destroystokyo.paper.entity.ai.GoalKey;
import com.destroystokyo.paper.entity.ai.VanillaGoal;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.utils.PriorityEntry;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Zombie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MobGoalManagerTest {

    private ServerMock server;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        MockBukkit.load(DiceMobManager.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testIgnoreGoalKey() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getIgnoredGoals()).hasSize(0);

        goalManager.ignoreGoal(VanillaGoal.HURT_BY_TARGET);
        assertThat(goalManager.getIgnoredGoals()).hasSize(1);
        assertThat(goalManager.getIgnoredGoals()).contains(VanillaGoal.HURT_BY_TARGET);
    }

    @Test
    void testIgnoreAllGoalKeys() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getIgnoredGoals()).hasSize(0);

        final List<GoalKey<?>> goalKeys = Arrays.asList(VanillaGoal.HURT_BY_TARGET, VanillaGoal.AVOID_TARGET);
        goalManager.ignoreAllGoals(goalKeys);

        assertThat(goalManager.getIgnoredGoals()).hasSize(2);
        assertThat(goalManager.getIgnoredGoals()).containsAll(goalKeys);
    }

    @Test
    void testAllowGoalKey() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getIgnoredGoals()).hasSize(0);

        goalManager.ignoreGoal(VanillaGoal.HURT_BY_TARGET);
        goalManager.allowGoal(VanillaGoal.HURT_BY_TARGET);
        assertThat(goalManager.getIgnoredGoals()).hasSize(0);
    }

    @Test
    void testAllowAllGoalKey() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getIgnoredGoals()).hasSize(0);

        final List<GoalKey<?>> goalKeys = Arrays.asList(VanillaGoal.HURT_BY_TARGET, VanillaGoal.AVOID_TARGET);
        goalManager.ignoreAllGoals(goalKeys);
        goalManager.allowAllGoals(goalKeys);
        assertThat(goalManager.getIgnoredGoals()).hasSize(0);
    }


    @Test
    void testAddCustomGoal() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getCustomGoals()).hasSize(0);
        final MockGoal mockGoal = new MockGoal();
        final Zombie zombie = new ZombieMock(server, UUID.randomUUID());

        goalManager.addCustomGoal(1, mob -> mockGoal);
        assertThat(goalManager.getCustomGoals()).hasSize(1);
        final PriorityEntry<GoalSupplier<Mob>> priorityEntry = goalManager.getCustomGoals().get(0);

        assertThat(priorityEntry.getPriority()).isEqualTo(1);
        assertThat(priorityEntry.getEntry().supply(zombie)).isEqualTo(mockGoal);
    }

    @Test
    void testAddAllCustomGoal() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getCustomGoals()).hasSize(0);
        final MockGoal firstMockGoal = new MockGoal();
        final MockGoal secondMockGoal = new MockGoal();
        final Zombie zombie = new ZombieMock(server, UUID.randomUUID());
        final List<PriorityEntry<GoalSupplier<Mob>>> goals = new ArrayList<>();
        goals.add(new PriorityEntry<>(1, mob -> firstMockGoal));
        goals.add(new PriorityEntry<>(2, mob -> secondMockGoal));

        goalManager.addAllCustomGoal(goals);
        assertThat(goalManager.getCustomGoals()).hasSize(2);
        final PriorityEntry<GoalSupplier<Mob>> firstEntry = goalManager.getCustomGoals().get(0);
        final PriorityEntry<GoalSupplier<Mob>> secondEntry = goalManager.getCustomGoals().get(1);

        assertThat(firstEntry.getPriority()).isEqualTo(1);
        assertThat(firstEntry.getEntry().supply(zombie)).isEqualTo(firstMockGoal);

        assertThat(secondEntry.getPriority()).isEqualTo(2);
        assertThat(secondEntry.getEntry().supply(zombie)).isEqualTo(secondMockGoal);
    }

    @Test
    void testChangePriority() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getCustomGoals()).hasSize(0);
        final MockGoal mockGoal = new MockGoal();
        final Zombie zombie = new ZombieMock(server, UUID.randomUUID());

        goalManager.addCustomGoal(1, mob -> mockGoal);
        goalManager.changePriority(2, MockGoal.GOAL_KEY);
        assertThat(goalManager.getCustomGoals()).hasSize(1);
        final PriorityEntry<GoalSupplier<Mob>> priorityEntry = goalManager.getCustomGoals().get(0);

        assertThat(priorityEntry.getPriority()).isEqualTo(2);
        assertThat(priorityEntry.getEntry().supply(zombie)).isEqualTo(mockGoal);
    }

    @Test
    void testChangePriorityGoalNotPresent() {
        final GoalManager goalManager = new MobGoalManager();
        assertThat(goalManager.getCustomGoals()).hasSize(0);

        goalManager.changePriority(2, MockGoal.GOAL_KEY);
        assertThat(goalManager.getCustomGoals()).hasSize(0);
    }

    @Test
    void testIgnoredGoalsListUnmodifiable() {
        final GoalManager goalManager = new MobGoalManager();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            goalManager.getIgnoredGoals().add(VanillaGoal.HURT_BY_TARGET);
        });
    }

    @Test
    void testCustomGoalsListUnmodifiable() {
        final GoalManager goalManager = new MobGoalManager();
        final MockGoal mockGoal = new MockGoal();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            goalManager.getCustomGoals().add(new PriorityEntry<>(1, mob -> mockGoal));
        });
    }
}