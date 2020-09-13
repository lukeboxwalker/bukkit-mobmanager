package de.dicecraft.dicemobmanager.command;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.SkullFactory;
import de.dicecraft.dicemobmanager.entity.builder.EntityInformation;
import de.dicecraft.dicemobmanager.entity.drops.CustomDeathDrop;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.equipment.CustomEquipment;
import de.dicecraft.dicemobmanager.entity.equipment.Equipment;
import de.dicecraft.dicemobmanager.entity.goals.GoalWalkToLocation;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnEntityCommand extends AbstractCommand {

    private final List<ItemStack> heads;

    public SpawnEntityCommand() {
        this.heads = new ArrayList<>();
        SkullFactory skullFactory = new SkullFactory();
        heads.add(skullFactory.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZlNjUwMmFjNGM4NDdiMWFjMzc4MTBkNjZkMjhjOTFhOGIxOGZkN2Y2MzgzMTI4MjI4NzU1YWE4YzhmNSJ9fX0="));
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                EntityType type = args.length == 1 ? EntityType.valueOf(args[0].toUpperCase()) : EntityType.ZOMBIE;
                DeathDrop deathDrop = new CustomDeathDrop(new ItemStack(Material.DIAMOND), 1, DeathDrop.Rarity.LEGENDARY);
                EntityInformation entityInformation = new EntityInformation();
                entityInformation.setDeathDrops(Collections.singletonList(deathDrop));

                Equipment equipment = new CustomEquipment();
                equipment.setItemInMainHand(new ItemStack(Material.IRON_SWORD));
                equipment.setChestPlate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                equipment.setHelmet(heads.get(0));

                DiceMobManager.builder(DiceMobManager.getInstance())
                        .atLocation(player.getLocation())
                        .fromType(type)
                        .attachGoal(1, mob -> new GoalWalkToLocation(mob, player.getLocation().clone().add(10, 0, 0)))
                        .setAttribute(Attribute.GENERIC_MAX_HEALTH, 1)
                        .useInformation(entityInformation)
                        .setEquipment(equipment)
                        .buildAndSpawn();
            } catch (EntityCreationException | IllegalArgumentException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<String> tabComplete(final CommandSender sender, final String[] args) {
        return new ArrayList<>();
    }
}
