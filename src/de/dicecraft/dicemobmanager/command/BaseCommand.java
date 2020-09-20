package de.dicecraft.dicemobmanager.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseCommand implements Command, CommandExecutor, TabCompleter {

    @Override
    public final boolean onCommand(@Nonnull CommandSender sender,
                                   @Nonnull org.bukkit.command.Command command,
                                   @Nonnull String name,
                                   @Nonnull String[] args) {
        return execute(sender, args);
    }

    @Override
    public final List<String> onTabComplete(@Nonnull CommandSender sender,
                                            @Nonnull org.bukkit.command.Command command,
                                            @Nonnull String name,
                                            @Nonnull String[] args) {
        final List<String> list = tabComplete(sender, args);
        return list == null ? new ArrayList<>() : list;
    }

    @Override
    public boolean execute(@Nonnull @Nonnull CommandSender sender, @Nonnull String @Nonnull [] args) {
        if (args.length == 0) {
            return true;
        } else {
            final String[] subArgument = getSubArguments(args);
            final Optional<SubCommand> optional = Arrays.stream(SubCommand.values())
                    .filter(command -> command.commandName.equals(args[0]))
                    .findAny();
            return optional.map(command -> command.command.execute(sender, subArgument)).orElse(false);
        }
    }

    @Override
    public List<String> tabComplete(@Nonnull @Nonnull CommandSender sender, @Nonnull String @Nonnull [] args) {
        if (args.length == 1) {
            return Arrays.stream(SubCommand.values())
                    .map(command -> command.commandName)
                    .filter(commandName -> commandName.startsWith(args[0]))
                    .collect(Collectors.toList());
        } else {
            final String[] subArgument = getSubArguments(args);
            final Optional<SubCommand> optional = Arrays.stream(SubCommand.values())
                    .filter(command -> command.commandName.equals(args[0]))
                    .findAny();
            return optional.map(command -> command.command.tabComplete(sender, subArgument)).orElse(new ArrayList<>());
        }
    }

    private String[] getSubArguments(String[] args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }

    private enum SubCommand {

        SPAWN_CUSTOM_MOB("spawnEntity", new SpawnEntityCommand()),
        CHANGE_ENTITY_TICK("changeTick", new ChangeEntityTickCommand());

        private final String commandName;
        private final Command command;

        SubCommand(final String commandName, final Command command) {
            this.commandName = commandName;
            this.command = command;
        }
    }
}
