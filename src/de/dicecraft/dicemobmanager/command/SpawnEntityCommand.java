package de.dicecraft.dicemobmanager.command;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpawnEntityCommand extends AbstractCommand {

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                DiceMobManager.getInstance().getSpawnManager().spawn(((Player) sender).getLocation());
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
