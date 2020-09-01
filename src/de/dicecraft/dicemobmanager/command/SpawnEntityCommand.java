package de.dicecraft.dicemobmanager.command;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.adapter.EntityCreationException;
import de.dicecraft.dicemobmanager.adapter.NMSHandler;
import de.dicecraft.dicemobmanager.entity.CustomEntity;
import de.dicecraft.dicemobmanager.entity.builder.Attribute;
import de.dicecraft.dicemobmanager.entity.builder.CustomEntityBuilder;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomGoalProvider;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpawnEntityCommand extends AbstractCommand {

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                try {
                    final CustomType<CustomEntity> customType = CustomType.getByName(args[0]);
                    final Location start = ((Player) sender).getLocation();
                    final Location goal = new Location(start.getWorld(), start.getX() + 10, start.getY(), start.getZ());
                    CustomEntityBuilder<CustomEntity> builder = CustomEntity.builder()
                            .fromType(customType)
                            .inWorld(((Player) sender).getWorld())
                            .isAggressive(true)
                            .attachGoalSelector(1, CustomGoalProvider.WALK_TO_LOCATION(goal))
                            .attachGoalSelector(2, CustomGoalProvider.MELEE_ATTACK())
                            .attachTargetSelector(1, CustomGoalProvider.HURT_BY_TARGET());

                    int entities = 1;
                    if (args.length == 2) {
                        try {
                            entities = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            sender.sendMessage("argument need to be numeric");
                        }
                    }

                    int finalEntities = Math.min(entities, 100);
                    if (args.length >= 3) {
                        for (int i = 2; i < args.length; i++) {
                            String[] split = args[i].split("=");
                            if (split.length == 2) {
                                try {
                                    builder.setAttribute(Attribute.valueOf(split[0].toUpperCase()), toDouble(split[1]));
                                } catch (IllegalArgumentException e) {
                                    sender.sendMessage("attribute type unknown");
                                }
                            }
                        }
                    }

                    new BukkitRunnable() {
                        private int spawnedEntities = 0;

                        @Override
                        public void run() {
                            if (spawnedEntities < finalEntities) {
                                try {
                                    NMSHandler.getEntityFactory().spawnCustomEntity(builder.build(), ((Player) sender).getLocation());
                                } catch (EntityCreationException e) {
                                    sender.sendMessage("could not spawn entity");
                                }
                                ++spawnedEntities;
                            } else {
                                cancel();
                            }
                        }
                    }.runTaskTimer(DiceMobManager.getInstance(), 0, 3);

                }catch (IllegalArgumentException e) {
                    sender.sendMessage("entityType unknown");
                    return true;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<String> tabComplete(final CommandSender sender, final String[] args) {
        if (args.length == 1) {
            return CustomType.values().stream()
                    .filter(name -> name.startsWith(args[0].toUpperCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            return Arrays.asList("1" ,"5", "10", "20");
        } else {
            return Arrays.stream(Attribute.values())
                    .map(attribute -> attribute.name() + "=")
                    .filter(name -> {
                        if (name.startsWith(args[args.length - 1].toUpperCase())) {
                            for (String arg : args) {
                                if (arg.startsWith(name)) {
                                    return false;
                                }
                            }
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList());
        }
    }

    public double toDouble(final String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return -1.0D;
        }
    }
}
