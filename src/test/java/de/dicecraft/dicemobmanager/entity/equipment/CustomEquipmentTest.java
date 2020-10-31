package de.dicecraft.dicemobmanager.entity.equipment;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


class CustomEquipmentTest {

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
    void testItemStacksCloned() {
        final ItemStack mainHand = new ItemStack(Material.DIAMOND_SWORD);
        final ItemStack offHand = new ItemStack(Material.DIAMOND_PICKAXE);
        final ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        final ItemStack chestPlate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        final ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
        final ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);

        final Equipment equipment = new CustomEquipment();
        equipment.setItemInMainHand(mainHand);
        equipment.setItemInOffHand(offHand);
        equipment.setHelmet(helmet);
        equipment.setChestPlate(chestPlate);
        equipment.setLeggings(leggings);
        equipment.setBoots(boots);

        final LivingEntity livingEntity = new ZombieMock(server, UUID.randomUUID());
        //TODO test equip() properly, mock entity not support equipment
        equipment.equip(livingEntity);

        final EntityEquipment entityEquipment = livingEntity.getEquipment();
        assertThat(entityEquipment).isNotNull();

        // memory address should be different because the item stack should be a clone of the original
        assertThat(mainHand == entityEquipment.getItemInMainHand()).isFalse();
        assertThat(offHand == entityEquipment.getItemInOffHand()).isFalse();
        assertThat(helmet == entityEquipment.getHelmet()).isFalse();
        assertThat(chestPlate == entityEquipment.getChestplate()).isFalse();
        assertThat(leggings == entityEquipment.getLeggings()).isFalse();
        assertThat(boots == entityEquipment.getBoots()).isFalse();

        // ensure the ItemStack has still the same properties
        assertThat(mainHand).isEqualTo(entityEquipment.getItemInMainHand());
        assertThat(offHand).isEqualTo(entityEquipment.getItemInOffHand());
        assertThat(helmet).isEqualTo(entityEquipment.getHelmet());
        assertThat(chestPlate).isEqualTo(entityEquipment.getChestplate());
        assertThat(leggings).isEqualTo(entityEquipment.getLeggings());
        assertThat(boots).isEqualTo(entityEquipment.getBoots());
    }

    @Test
    void dropChanceShouldBeZero() {
        final Equipment equipment = new CustomEquipment();
        final LivingEntity livingEntity = new ZombieMock(server, UUID.randomUUID());
        //TODO test equip() properly, mock entity not support equipment
        equipment.equip(livingEntity);

        final EntityEquipment entityEquipment = livingEntity.getEquipment();
        assertThat(entityEquipment).isNotNull();

        assertThat(entityEquipment.getItemInMainHandDropChance()).isLessThanOrEqualTo(0);
        assertThat(entityEquipment.getItemInOffHandDropChance()).isLessThanOrEqualTo(0);
        assertThat(entityEquipment.getHelmetDropChance()).isLessThanOrEqualTo(0);
        assertThat(entityEquipment.getChestplateDropChance()).isLessThanOrEqualTo(0);
        assertThat(entityEquipment.getLeggingsDropChance()).isLessThanOrEqualTo(0);
        assertThat(entityEquipment.getBootsDropChance()).isLessThanOrEqualTo(0);
    }

    @Test
    void testToString() {
        final Equipment equipment = new CustomEquipment();
        assertThat(equipment.toString().isEmpty()).isFalse();
    }
}