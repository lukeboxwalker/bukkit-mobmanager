package de.dicecraft.dicemobmanager.command;

import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BaseCommand implements Command {

    private final List<Command> subCommands;
    private final String name;

    public BaseCommand(final String name, final Command... subCommands) {
        this.name = name;
        this.subCommands = Arrays.asList(subCommands);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean execute(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        if (args.length == 0) {
            return true;
        } else {
            final String[] subArgument = getSubArguments(args);
            final Optional<Command> optional = subCommands.stream()
                    .filter(command -> command.getName().equals(args[0]))
                    .findAny();
            return optional.map(command -> command.execute(sender, subArgument)).orElse(false);
        }
    }

    @Override
    public List<String> tabComplete(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        if (args.length == 1) {
            return subCommands.stream()
                    .map(Command::getName)
                    .filter(commandName -> commandName.startsWith(args[0]))
                    .collect(Collectors.toList());
        } else {
            final String[] subArgument = getSubArguments(args);
            final Optional<Command> optional = subCommands.stream()
                    .filter(command -> command.getName().equals(args[0]))
                    .findAny();
            return optional.map(command -> command.tabComplete(sender, subArgument)).orElse(new ArrayList<>());
        }
    }

    private String[] getSubArguments(final String... args) {
        return Arrays.copyOfRange(args, 1, args.length);
    }
}
