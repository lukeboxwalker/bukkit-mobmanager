package de.dicecraft.dicemobmanager.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Specifies the execution of a command.
 *
 * Can also implement the tabCompleter for a specific
 * command if needed.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    @Override
    public final boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String name, @Nonnull String[] args) {
        return execute(sender, args);
    }

    /**
     * Executes the command.
     *
     * @param sender who wants to execute the command
     * @param args the arguments for the command
     * @return if command executes successful
     */
    public abstract boolean execute(final CommandSender sender, final String[] args);

    @Override
    public final List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String name, @Nonnull String[] args) {
        final  List<String> list = tabComplete(sender, args);
        return list == null ? new ArrayList<>() : list;
    }


    /**
     * Provides tab completion.
     *
     * Pressing tab on a specific command in chat,
     * results in a string list of possible arguments that
     * can fit in this position.
     *
     * @param sender who wants to the possible arguments
     * @param args current arguments of the command
     * @return list of possible tab completions
     */
    public abstract List<String> tabComplete(final CommandSender sender, final String[] args);
}
