package de.dicecraft.dicemobmanager.configuration;

import de.dicecraft.dicemobmanager.entity.drops.CustomDeathDrop;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CustomConfigurationTest {

    @Test
    void setFlagCorrectly() {
        final CustomConfiguration configuration = new CustomConfiguration();
        final Map<ConfigFlag, Boolean> flagMap = new HashMap<>();
        flagMap.put(ConfigFlag.CREEPER_EXPLOSION_DAMAGE, true);
        flagMap.put(ConfigFlag.SHOW_WITHER_BOSS_BAR, false);
        configuration.setConfigFlags(flagMap);

        // flags should be the same as set
        assertThat(configuration.shouldCancel(ConfigFlag.CREEPER_EXPLOSION_DAMAGE)).isFalse();
        assertThat(configuration.shouldCancel(ConfigFlag.SHOW_WITHER_BOSS_BAR)).isTrue();
    }

    @Test
    void setDropHandlerCorrectly() {
        final CustomConfiguration configuration = new CustomConfiguration();
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, 1, DeathDrop.Rarity.RARE);
        final List<DeathDrop> deathDrops = Collections.singletonList(deathDrop);
        configuration.setHandler(drops -> new ArrayList<>());
        final List<DeathDrop> handledDeathDrops = configuration.getItemDropHandler().handleDrops(deathDrops);

        // list should not be the same
        assertThat(deathDrops == handledDeathDrops).isFalse();
        assertThat(deathDrops).isNotEqualTo(handledDeathDrops);

        assertThat(handledDeathDrops).hasSize(0);
    }

    @Test
    void testDefaultFlagTrue() {
        final Configuration configuration = new CustomConfiguration();
        Arrays.stream(ConfigFlag.values()).forEach(flag -> assertThat(configuration.shouldCancel(flag)).isFalse());
    }

    @Test
    void testDefaultDropHandlerEmpty() {
        final Configuration configuration = new CustomConfiguration();
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, 1, DeathDrop.Rarity.RARE);
        final List<DeathDrop> deathDrops = Collections.singletonList(deathDrop);
        final List<DeathDrop> handledDeathDrops = configuration.getItemDropHandler().handleDrops(deathDrops);

        // check if its actually the same list
        assertThat(deathDrops == handledDeathDrops).isTrue();
        assertThat(deathDrops).isEqualTo(handledDeathDrops);
    }

    @Test
    void testToString() {
        final Configuration configuration = new CustomConfiguration();
        assertThat(configuration.toString().isEmpty()).isFalse();
    }
}