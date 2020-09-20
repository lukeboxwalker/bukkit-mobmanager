package de.dicecraft.dicemobmanager.command.subcommand.tick;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.command.Command;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ChangeEntityTickCommand implements Command {

    @Override
    public String getName() {
        return "changeTick";
    }

    @Override
    public boolean execute(@Nonnull final CommandSender sender, @Nonnull final String[] args) {
        if (args.length == 1) {
            try {
                int ticks = Integer.parseInt(args[0]);
                DiceMobManager.restartScheduler(ticks);
                sender.sendMessage("§8[§aDiceMobManager§8] §7Scheduler restarted, entities will "
                        + "now get a mob tick every §5" + ticks + " §7game tick(s).");
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
    public List<String> tabComplete(@Nonnull final CommandSender sender, @Nonnull final String[] args) {
        return Arrays.asList("1", "10", "20");
    }
}
