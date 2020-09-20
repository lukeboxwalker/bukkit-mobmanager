package de.dicecraft.dicemobmanager.command;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Managing Command registration.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public final class CommandManager {

    private static final String BASE_COMMAND_STRING = "MobManager";

    private CommandManager() {
    }

    /**
     * Registers the base plugin command.
     *
     * @param plugin to register commands
     */
    public static void registerCommands(final JavaPlugin plugin) {
        final BaseCommand baseCommand = new BaseCommand();
        final PluginCommand command = plugin.getCommand(BASE_COMMAND_STRING);
        assert command != null;
        command.setExecutor(baseCommand);
        command.setTabCompleter(baseCommand);
    }
}
