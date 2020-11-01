package de.dicecraft.dicemobmanager.entity.drops;

import be.seeseemelk.mockbukkit.MockBukkit;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomDeathDropTest {

    private static final double TEST_DROP_CHANCE = 0.5;

    @BeforeEach
    public void setUp() {
        MockBukkit.mock();
        MockBukkit.load(DiceMobManager.class);
    }

    @AfterEach
    public void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void itemStackShouldBeCloned() {
        ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, TEST_DROP_CHANCE, DeathDrop.Rarity.RARE);

        // memory address should be different because the item stack should be a clone of the original
        assertThat(itemStack == deathDrop.getItemStack()).isFalse();

        // ensure the ItemStack has still the same properties
        assertThat(itemStack).isEqualTo(deathDrop.getItemStack());

        // setting ItemStack by using the setter
        itemStack = new ItemStack(Material.EMERALD);
        deathDrop.setItemStack(itemStack);

        // memory address should be different because the item stack should be a clone of the original
        assertThat(itemStack == deathDrop.getItemStack()).isFalse();

        // ensure the ItemStack has still the same properties
        assertThat(itemStack).isEqualTo(deathDrop.getItemStack());
    }

    @Test
    void raritySetCorrectly() {
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, TEST_DROP_CHANCE, DeathDrop.Rarity.RARE);

        // ensure the rarity is the one set during construction
        assertThat(deathDrop.getRarity()).isEqualTo(DeathDrop.Rarity.RARE);

        deathDrop.setRarity(DeathDrop.Rarity.LEGENDARY);

        // ensure the rarity is the one set by using the setter
        assertThat(deathDrop.getRarity()).isEqualTo(DeathDrop.Rarity.LEGENDARY);
    }

    @Test
    void dropChanceNotHigherThanOne() {
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, 2, DeathDrop.Rarity.RARE);
        assertThat(deathDrop.getDropChance()).isLessThanOrEqualTo(1);

        deathDrop.setDropChance(2);
        assertThat(deathDrop.getDropChance()).isLessThanOrEqualTo(1);
    }

    @Test
    void dropChanceNotLowerThanZero() {
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, -1, DeathDrop.Rarity.RARE);
        assertThat(deathDrop.getDropChance()).isGreaterThanOrEqualTo(0);

        deathDrop.setDropChance(-1);
        assertThat(deathDrop.getDropChance()).isGreaterThanOrEqualTo(0);
    }

    @Test
    void shouldNeverDrop() {
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, 0, DeathDrop.Rarity.RARE);

        // test if deathDrop should drop
        assertThat(deathDrop.shouldDrop(0)).isFalse();
    }

    @Test
    void shouldAlwaysDrop() {
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, 1, DeathDrop.Rarity.RARE);

        // test if deathDrop should drop
        assertThat(deathDrop.shouldDrop(0)).isTrue();
    }

    @Test
    void testToString() {
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        final DeathDrop deathDrop = new CustomDeathDrop(itemStack, 1, DeathDrop.Rarity.RARE);
        assertThat(deathDrop.toString().isEmpty()).isFalse();
    }
}