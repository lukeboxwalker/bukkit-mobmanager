package de.dicecraft.dicemobmanager.command;

/**
 * Contains all commands for this plugin.
 *
 * Associates the correct command implementation to
 * a given command string specified in plugin.yml.
 * Acts as a container class for the actual command executor
 * and tabCompleter {@link AbstractCommand}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public enum Command {

    SPAWN_MOB("spawnEntity", new SpawnEntityCommand());

    private final String name;
    private final AbstractCommand command;

    Command(final String name, final AbstractCommand command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public AbstractCommand getCommand() {
        return command;
    }
}
