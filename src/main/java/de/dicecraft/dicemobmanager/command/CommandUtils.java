package de.dicecraft.dicemobmanager.command;

import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.message.MessageFormatter;
import de.dicecraft.dicemobmanager.message.PluginMessageFormatter;
import de.dicecraft.dicemobmanager.message.TextComponentBuilder;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Managing Command registration.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public final class CommandUtils {

    private static final MessageFormatter MESSAGE_FORMATTER = new PluginMessageFormatter();

    private CommandUtils() {
    }

    /**
     * Registers the base plugin command.
     *
     * @param plugin  to register commands
     * @param manager the entity manager
     */
    public static void registerCommands(final JavaPlugin plugin, final EntityManager manager) {
        final BasePluginCommand baseCommand = new BasePluginCommand(manager);
        final PluginCommand command = plugin.getCommand(baseCommand.getName());
        if (command != null) {
            command.setExecutor(baseCommand);
            command.setTabCompleter(baseCommand);
        }
    }

    public static MessageFormatter messageFormatter() {
        return MESSAGE_FORMATTER;
    }

    public static TextComponentBuilder componentBuilder() {
        return new TextComponentBuilder();
    }
}
