package de.dicecraft.dicemobmanager.command;

import de.dicecraft.dicemobmanager.command.subcommand.kill.KillEntityCommand;
import de.dicecraft.dicemobmanager.command.subcommand.tick.ChangeEntityTickCommand;
import de.dicecraft.dicemobmanager.command.subcommand.spawn.SpawnEntityCommand;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BasePluginCommand extends BaseCommand implements CommandExecutor, TabCompleter {

    public BasePluginCommand(final EntityManager manager) {
        super("MobManager", new SpawnEntityCommand(),
                new ChangeEntityTickCommand(), new KillEntityCommand(manager),
                new TestCommand());
    }

    @Override
    public final boolean onCommand(@Nonnull CommandSender sender, @Nonnull org.bukkit.command.Command command,
                                   @Nonnull String name, @Nonnull String[] args) {
        return execute(sender, args);
    }

    @Override
    public final List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull org.bukkit.command.Command command,
                                            @Nonnull String name, @Nonnull String[] args) {
        final List<String> list = tabComplete(sender, args);
        return list == null ? new ArrayList<>() : list;
    }
}
