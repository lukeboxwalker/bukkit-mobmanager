package de.dicecraft.dicemobmanager.command.subcommand.tick;

import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.command.Command;
import de.dicecraft.dicemobmanager.command.CommandUtils;
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
                final int ticks = Integer.parseInt(args[0]);
                DiceMobManager.restartScheduler(ticks);
                CommandUtils.messageFormatter().sendMessage(sender,
                        "§7Scheduler restarted, entities will now get a mob tick every §5{0} §7game tick(s).",
                        ticks);

                return true;
            } catch (NumberFormatException e) {
                CommandUtils.messageFormatter().sendMessage(sender,
                        "§7Argument '§5{0}§7' is not numeric!",
                        args[0]);
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
