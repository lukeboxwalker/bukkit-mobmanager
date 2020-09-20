package de.dicecraft.dicemobmanager.command.subcommand.kill.subcommand;

import de.dicecraft.dicemobmanager.command.Command;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class KillRadiusCommand implements Command {

    private final EntityManager manager;

    public KillRadiusCommand(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "-r";
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        return false;
    }

    @Override
    public List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String[] args) {
        return new ArrayList<>();
    }
}
