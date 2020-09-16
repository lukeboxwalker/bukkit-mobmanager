package de.dicecraft.dicemobmanager.command;

import de.dicecraft.dicemobmanager.DiceMobManager;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class ChangeEntityTickCommand extends AbstractCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 1) {
            try {
                int ticks = Integer.parseInt(args[0]);
                DiceMobManager.restartScheduler(ticks);
                sender.sendMessage("Scheduler restarted, entities will now gets a mob tick every " + ticks + " game tick(s).");
                return true;
            } catch (NumberFormatException e) {
                sender.sendMessage("Argument '" + args[0] + "' is not numeric!");
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Arrays.asList("1", "10", "20");
    }
}