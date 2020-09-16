package de.dicecraft.dicemobmanager.command;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.SkullFactory;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.CustomDeathDrop;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.factory.Factory;
import de.dicecraft.dicemobmanager.entity.goals.GoalWalkToLocation;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import de.dicecraft.dicemobmanager.entity.strategy.SpiderBossTickStrategy;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
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
                DeathDrop deathDrop = new CustomDeathDrop(new ItemStack(Material.DIAMOND), 1, DeathDrop.Rarity.LEGENDARY);

                ProtoEntity entity = DiceMobManager.builder()
                        .setType(EntityType.SPIDER)
                        .setDeathDrops(Collections.singletonList(deathDrop))
                        .addEquipment(EquipmentSlot.HAND, new ItemStack(Material.IRON_SWORD))
                        .addEquipment(EquipmentSlot.CHEST, new ItemStack(Material.DIAMOND_CHESTPLATE))
                        .addEquipment(EquipmentSlot.HEAD, heads.get(0))
                        .addGoal(1, mob -> new GoalWalkToLocation(mob, player.getLocation().clone().add(10, 0, 0)))
                        .removeGoal(VanillaGoal.NEAREST_ATTACKABLE_TARGET)
                        .setAttribute(Attribute.GENERIC_MAX_HEALTH, 1)
                        .setName("Mutter Spinne")
                        .setStrategy(StrategyType.ON_TICK, new SpiderBossTickStrategy())
                        .build();

                Factory factory = new Factory();
                factory.spawnEntity(entity, player.getLocation());

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
