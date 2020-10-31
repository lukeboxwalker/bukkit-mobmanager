package de.dicecraft.dicemobmanager.entity.name;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.attribute.AttributeInstanceMock;
import be.seeseemelk.mockbukkit.entity.LivingEntityMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomNameUpdaterTest {

    private static final String LIGHT_GREEN = String.valueOf(ChatColor.COLOR_CHAR) + ChatColor.GREEN.getChar();
    private static final String YELLOW = String.valueOf(ChatColor.COLOR_CHAR) + ChatColor.YELLOW.getChar();

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
    void testHealthFormatting() {
        final ProtoEntity<? extends Mob> protoEntity = DiceMobManager.builder(CustomType.ZOMBIE).build();
        final CustomNameUpdater customNameUpdater = new CustomNameUpdater(protoEntity);

        assertThat(customNameUpdater.formatHealth(1)).isEqualTo("1");
        assertThat(customNameUpdater.formatHealth(10)).isEqualTo("10");
        assertThat(customNameUpdater.formatHealth(100)).isEqualTo("100");
        assertThat(customNameUpdater.formatHealth(1_000)).isEqualTo("1k");
        assertThat(customNameUpdater.formatHealth(10_000)).isEqualTo("10k");
        assertThat(customNameUpdater.formatHealth(100_000)).isEqualTo("100k");
        assertThat(customNameUpdater.formatHealth(1_000_000)).isEqualTo("1m");
        assertThat(customNameUpdater.formatHealth(10_000_000)).isEqualTo("10m");
        assertThat(customNameUpdater.formatHealth(100_000_000)).isEqualTo("100m");
    }

    @Test
    void testBuildName() {
        final String name = "Test-Name";
        final int level = 100;
        final LivingEntity livingEntity = new ZombieMock(server, UUID.randomUUID());
        final ProtoEntity<? extends Mob> protoEntity = DiceMobManager.builder(CustomType.ZOMBIE)
                .setName(name).setLevel(level).build();
        final NameUpdater nameUpdater = new CustomNameUpdater(protoEntity);

        assertThat(nameUpdater.buildName(livingEntity)).contains(name);
        assertThat(nameUpdater.buildName(livingEntity)).contains(String.valueOf(level));
    }

    @Test
    void testColorOfHealth() {
        final LivingEntity livingEntity = new ZombieMock(server, UUID.randomUUID());
        final ProtoEntity<? extends Mob> protoEntity = DiceMobManager.builder(CustomType.ZOMBIE).build();
        final NameUpdater nameUpdater = new CustomNameUpdater(protoEntity);

        assertThat(nameUpdater.buildName(livingEntity)).contains(LIGHT_GREEN);
        assertThat(nameUpdater.buildName(livingEntity)).doesNotContain(YELLOW);

        final double health = livingEntity.getHealth();

        livingEntity.setHealth(health / 2);
        assertThat(nameUpdater.buildName(livingEntity)).contains(YELLOW);
    }

    @Test
    @SuppressWarnings("unchecked")
    void testHealthNotExists() {
        final LivingEntity livingEntity = new ZombieMock(server, UUID.randomUUID());
        final ProtoEntity<? extends Mob> protoEntity = DiceMobManager.builder(CustomType.ZOMBIE).build();
        final NameUpdater nameUpdater = new CustomNameUpdater(protoEntity);

        Map<Attribute, AttributeInstanceMock> attributes = null;
        try {
            final Field field = LivingEntityMock.class.getDeclaredField("attributes");
            field.setAccessible(true);
            attributes = (Map<Attribute, AttributeInstanceMock>) field.get(livingEntity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        assertThat(attributes).isNotNull();
        attributes.remove(Attribute.GENERIC_MAX_HEALTH);

        //TODO test customName properly, mock entity not support customName
        assertThat(nameUpdater.buildName(livingEntity)).isEqualTo(livingEntity.getCustomName());
    }

    @Test
    void testUpdateName() {
        final LivingEntity livingEntity = new ZombieMock(server, UUID.randomUUID());
        final ProtoEntity<? extends Mob> protoEntity = DiceMobManager.builder(CustomType.ZOMBIE).build();
        final NameUpdater nameUpdater = new CustomNameUpdater(protoEntity);

        //TODO test customName properly, mock entity not support customName
        final String customName = livingEntity.getCustomName();
        nameUpdater.updateName(livingEntity);

        assertThat(livingEntity.getCustomName()).isNotEqualTo(customName);
    }
}