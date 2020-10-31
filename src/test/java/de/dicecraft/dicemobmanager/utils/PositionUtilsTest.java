package de.dicecraft.dicemobmanager.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionUtilsTest {

    private World world;

    @BeforeEach
    public void setUp() {
        ServerMock server = MockBukkit.mock();
        MockBukkit.load(DiceMobManager.class);
        world = server.addSimpleWorld("Test-World");
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testEntityDistanceIsOne() {
        final Entity zeroEntity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
        assertThat(PositionUtils.distanceSquared(zeroEntity,
                world.spawnEntity(new Location(world, 1, 0, 0), EntityType.ZOMBIE))).isEqualTo(1);
        assertThat(PositionUtils.distanceSquared(zeroEntity,
                world.spawnEntity(new Location(world, 0, 1, 0), EntityType.ZOMBIE))).isEqualTo(1);
        assertThat(PositionUtils.distanceSquared(zeroEntity,
                world.spawnEntity(new Location(world, 0, 0, 1), EntityType.ZOMBIE))).isEqualTo(1);
    }

    @Test
    void testEntityDistanceIsZero() {
        final Entity zeroEntity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE);
        assertThat(PositionUtils.distanceSquared(zeroEntity,
                world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ZOMBIE))).isEqualTo(0);
        assertThat(PositionUtils.distanceSquared(zeroEntity, zeroEntity)).isEqualTo(0);
    }

    @Test
    void testLocationDistanceIsOne() {
        final Location zero = new Location(world, 0, 0, 0);
        assertThat(PositionUtils.distanceSquared(zero, new Location(world, 1, 0, 0))).isEqualTo(1);
        assertThat(PositionUtils.distanceSquared(zero, new Location(world, 0, 1, 0))).isEqualTo(1);
        assertThat(PositionUtils.distanceSquared(zero, new Location(world, 0, 0, 1))).isEqualTo(1);
    }

    @Test
    void testLocationDistanceIsZero() {
        final Location zero = new Location(world, 0, 0, 0);
        assertThat(PositionUtils.distanceSquared(zero, new Location(world, 0, 0, 0))).isEqualTo(0);
        assertThat(PositionUtils.distanceSquared(zero, zero)).isEqualTo(0);
    }

    @Test
    void testVectorDistanceIsOne() {
        final Vector zero = new Vector(0, 0, 0);
        assertThat(PositionUtils.distanceSquared(zero, new Vector(1, 0, 0))).isEqualTo(1);
        assertThat(PositionUtils.distanceSquared(zero, new Vector(0, 1, 0))).isEqualTo(1);
        assertThat(PositionUtils.distanceSquared(zero, new Vector(0, 0, 1))).isEqualTo(1);
    }

    @Test
    void testVectorDistanceIsZero() {
        final Vector zero = new Vector(0, 0, 0);
        assertThat(PositionUtils.distanceSquared(zero, new Vector(0, 0, 0))).isEqualTo(0);
        assertThat(PositionUtils.distanceSquared(zero, zero)).isEqualTo(0);
    }
}