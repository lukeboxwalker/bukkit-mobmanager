package de.dicecraft.dicemobmanager.command;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface Command {

    /**
     * Executes the command.
     *
     * @param sender who wants to execute the command
     * @param args the arguments for the command
     * @return if command executes successful
     */
    boolean execute(final CommandSender sender, final String[] args);

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
    List<String> tabComplete(final CommandSender sender, final String[] args);
}
