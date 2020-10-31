package de.dicecraft.dicemobmanager.command.subcommand.info;

import de.dicecraft.dicemobmanager.command.Command;
import de.dicecraft.dicemobmanager.command.CommandUtils;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.message.TextComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class InfoCommand implements Command {

    private static final String SPACE = " ";
    private static final String TP_COMMAND = "/minecraft:tp";
    private static final String MESSAGE = "§7Entity '{0}§7' at location: §8[{1}§8]§7.";
    private static final String NOT_ALIVE = "§7No entities alive.";
    private static final Function<Entity, TextComponentBuilder.RunnableCommand> ON_CLICK = entity -> () -> {
        final Location location = entity.getLocation();
        return TP_COMMAND + SPACE + formatLocation(location);
    };

    private final EntityManager entityManager;

    public InfoCommand(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public boolean execute(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        final Set<Entity> entities = entityManager.getAllEntities().keySet();
        if (entities.isEmpty()) {
            CommandUtils.messageFormatter().sendMessage(sender, NOT_ALIVE);
        } else {
            entities.forEach(entity -> {
                final Location location = entity.getLocation();
                CommandUtils.messageFormatter().sendMessage(sender, MESSAGE,
                        CommandUtils.componentBuilder()
                                .setColor(ChatColor.DARK_PURPLE)
                                .setText(entity.getType().name().toLowerCase())
                                .build(),
                        CommandUtils.componentBuilder()
                                .setColor(ChatColor.DARK_PURPLE)
                                .setText(formatLocation(location))
                                .addClickCommand(ON_CLICK.apply(entity)).build());
            });
        }
        return true;
    }

    private static String formatLocation(final Location location) {
        return (int) location.getX() + SPACE + (int) location.getY() + SPACE + (int) location.getZ();
    }

    @Override
    public List<String> tabComplete(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        return new ArrayList<>();
    }
}
