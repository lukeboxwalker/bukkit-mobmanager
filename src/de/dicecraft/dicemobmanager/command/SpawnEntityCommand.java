package de.dicecraft.dicemobmanager.command;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.CustomEntities;
import de.dicecraft.dicemobmanager.entity.goals.GoalWalkToLocation;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpawnEntityCommand extends AbstractCommand {

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (sender instanceof Player && args.length == 1) {
            Player player = (Player) sender;

            LinkedList<Location> locations = new LinkedList<>();
            locations.add(new Location(player.getWorld(), 317, 90, 55));
            locations.add(new Location(player.getWorld(), 322, 90, 57));
            locations.add(new Location(player.getWorld(), 324, 90, 61));
            locations.add(new Location(player.getWorld(), 324, 90, 65));

            try {
                CustomEntities.builder(DiceMobManager.getInstance())
                        .atLocation(player.getLocation())
                        .fromType(EntityType.valueOf(args[0].toUpperCase()))
                        .attachGoalSelector(1, mob -> new GoalWalkToLocation(mob, locations))
                        .setAttribute(Attribute.GENERIC_MAX_HEALTH, 1)
                        .build();
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
