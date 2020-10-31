package de.dicecraft.dicemobmanager.command.subcommand.kill.subcommand;

import de.dicecraft.dicemobmanager.command.Command;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class KillRadiusCommand implements Command {


    @Override
    public String getName() {
        return "-r";
    }

    @Override
    public boolean execute(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        return false;
    }

    @Override
    public List<String> tabComplete(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        return new ArrayList<>();
    }
}
