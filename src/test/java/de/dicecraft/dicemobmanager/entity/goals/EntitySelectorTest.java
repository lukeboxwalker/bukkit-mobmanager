package de.dicecraft.dicemobmanager.entity.goals;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EntitySelectorTest {

    private ServerMock server;
    private World world;
    private Location location;

    @BeforeEach
    public void setUp() {
        server = MockBukkit.mock();
        MockBukkit.load(DiceMobManager.class);
        world = server.addSimpleWorld("Test-World");
        location = new Location(world, 0, 0, 0);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testPlayerInSurvival() {
        final PlayerMock player = server.addPlayer();
        player.setGameMode(GameMode.SURVIVAL);
        assertThat(EntitySelector.IS_PLAYER_IN_SURVIVAL.test(player)).isTrue();

        player.setGameMode(GameMode.ADVENTURE);
        assertThat(EntitySelector.IS_PLAYER_IN_SURVIVAL.test(player)).isTrue();
    }

    @Test
    void testPlayerNotSurvival() {
        final PlayerMock player = server.addPlayer();
        player.setGameMode(GameMode.CREATIVE);
        assertThat(EntitySelector.IS_PLAYER_IN_SURVIVAL.test(player)).isFalse();

        player.setGameMode(GameMode.SPECTATOR);
        assertThat(EntitySelector.IS_PLAYER_IN_SURVIVAL.test(player)).isFalse();
    }

    @Test
    void testIsSlimeEntity() {
        Entity entity = world.spawnEntity(location, EntityType.SLIME);
        assertThat(EntitySelector.IS_SLIME_ENTITY.test(entity)).isTrue();

        entity = world.spawnEntity(location, EntityType.MAGMA_CUBE);
        assertThat(EntitySelector.IS_SLIME_ENTITY.test(entity)).isTrue();
    }

    @Test
    void testIsNotSlimeEntity() {
        final Entity entity = world.spawnEntity(location, EntityType.ZOMBIE);
        assertThat(EntitySelector.IS_SLIME_ENTITY.test(entity)).isFalse();
    }

    @Test
    void testIsProjectile() {
        Entity entity = world.spawnEntity(location, EntityType.WITHER_SKULL);
        assertThat(EntitySelector.IS_PROJECTILE.test(entity)).isTrue();

        entity = world.spawnEntity(location, EntityType.FIREBALL);
        assertThat(EntitySelector.IS_PROJECTILE.test(entity)).isTrue();
    }

    @Test
    void testIsNotProjectile() {
        final Entity entity = world.spawnEntity(location, EntityType.ZOMBIE);
        assertThat(EntitySelector.IS_PROJECTILE.test(entity)).isFalse();
    }
}