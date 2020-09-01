package de.dicecraft.dicemobmanager.command;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * Managing Command registration.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public final class CommandManager {

    /**
     * Registers all plugin commands.
     *
     * Commands can be found in {@link Command}, which links the
     * valid command string to its implementation class.
     * @param plugin to register commands
     */
    public static void registerCommands(final JavaPlugin plugin) {
        Arrays.stream(Command.values()).forEach(commandContainer -> {
            final PluginCommand command = plugin.getCommand(commandContainer.getName());
            assert command != null;
            command.setExecutor(commandContainer.getCommand());
            command.setTabCompleter(commandContainer.getCommand());
        });
    }

}
