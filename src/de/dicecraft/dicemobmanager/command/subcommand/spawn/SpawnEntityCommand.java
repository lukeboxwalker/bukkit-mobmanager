package de.dicecraft.dicemobmanager.command.subcommand.spawn;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.command.Command;
import de.dicecraft.dicemobmanager.command.CommandUtils;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.CustomDeathDrop;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.factory.EntitySpawnFactory;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;

import de.dicecraft.dicemobmanager.entity.goals.CustomGoal;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnEntityCommand implements Command {

    private static final String SINGLE_SPAWN =
            "§7Entity  '§8[§7Lv§5{1}§8]§7' '§5{0}§7' spawned as a '§5{2}§7' mob.";
    private static final String MULTI_SPAWN =
            "§7Entity '§8[§7Lv§5{1}§8]§7' '§5{0}§7' with spawned as a '§5{2}§7' mob, §5{3} §7times";

    private static final double DROP_CHANCE = 1;
    private static final int PERIOD = 1;

    private static final int LEVEL = 100;
    private static final double HEALTH = 5;
    private static final double RADIUS = 10;

    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public boolean execute(final @Nonnull CommandSender sender, @Nonnull final String[] args) {
        if (sender instanceof Player) {
            int amount = 1;
            if (args.length == 1) {
                try {
                    amount = Integer.parseInt(args[0]);
                    amount = Math.max(amount, 1);
                } catch (NumberFormatException e) {
                    CommandUtils.messageFormatter().sendMessage(sender,
                            "§7Argument '§5{0}§7' is not numeric!",
                            args[0]);
                    return false;
                }
            }
            final Player player = (Player) sender;
            final int finalAmount = amount;
            new BukkitRunnable() {
                private int spawnedEntities = 0;

                @Override
                public void run() {
                    if (spawnedEntities < finalAmount) {
                        try {
                            final ItemStack itemStack = new ItemStack(Material.DIAMOND);
                            final DeathDrop deathDrop = new CustomDeathDrop(itemStack, DROP_CHANCE,
                                    DeathDrop.Rarity.LEGENDARY);

                            final ProtoEntity<? extends Mob> protoEntity = DiceMobManager.builder(CustomType.ZOMBIE)
                                    .setDeathDrops(Collections.singleton(deathDrop))
                                    .setAttribute(Attribute.GENERIC_MAX_HEALTH, HEALTH)
                                    .setName("Test Mob")
                                    .setLevel(LEVEL)
                                    .ignoreGoal(VanillaGoal.NEAREST_ATTACKABLE_TARGET)
                                    .addGoal(1, CustomGoal.nearestTarget(Player.class, RADIUS))
                                    .build();

                            final Configuration configuration = DiceMobManager.configBuilder().denyAllFlags().build();

                            final EntitySpawnFactory factory = DiceMobManager.createSpawnFactory(configuration);
                            factory.spawnEntity(protoEntity, player.getLocation());
                            if (spawnedEntities == 0) {
                                if (finalAmount > 1) {
                                    CommandUtils.messageFormatter().sendMessage(sender,
                                            MULTI_SPAWN,
                                            protoEntity.getName(),
                                            protoEntity.getLevel(),
                                            protoEntity.getCustomType().getEntityType().name().toLowerCase(),
                                            finalAmount);
                                } else {
                                    CommandUtils.messageFormatter().sendMessage(sender,
                                            SINGLE_SPAWN,
                                            protoEntity.getName(),
                                            protoEntity.getLevel(),
                                            protoEntity.getCustomType().getEntityType().name().toLowerCase());
                                }
                            }
                        } catch (EntityCreationException e) {
                            e.printStackTrace();
                        }
                        ++spawnedEntities;
                    } else {
                        cancel();
                    }
                }
            }.runTaskTimer(DiceMobManager.getInstance(), 0, PERIOD);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> tabComplete(final @Nonnull CommandSender sender, @Nonnull final String[] args) {
        return new ArrayList<>();
    }
}
