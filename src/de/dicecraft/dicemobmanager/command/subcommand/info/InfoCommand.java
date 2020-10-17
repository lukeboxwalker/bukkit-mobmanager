package de.dicecraft.dicemobmanager.command.subcommand.info;

import de.dicecraft.dicemobmanager.command.Command;
import de.dicecraft.dicemobmanager.command.CommandManager;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.message.TextComponentBuilder;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class InfoCommand implements Command {

    private final EntityManager entityManager;

    public InfoCommand(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Function<Entity, TextComponentBuilder.RunnableCommand> onClick = entity -> () -> {
                final Location location = entity.getLocation();
                final String empty = " ";
                return "/tp " + location.getX() + empty + location.getY() + empty + location.getZ();
            };
            final Set<Entity> entities = entityManager.getAllEntities().keySet();
            if (entities.isEmpty()) {
                CommandManager.messageFormatter().sendMessage(sender, "§7No entities alive.");
            }
            entities.forEach(entity -> {
                Location location = entity.getLocation();
                CommandManager.messageFormatter().sendMessage(player,
                        "§7Entity '{0}§7' at location: '{1} {2} {3}§7'.",
                        CommandManager.componentBuilder().setText("§5" + entity.getType().name().toLowerCase())
                                .build(),
                        CommandManager.componentBuilder().setText("§5" + (int) location.getX())
                                .addClickCommand(onClick.apply(entity)).build(),
                        CommandManager.componentBuilder().setText("§5" + (int) location.getY())
                                .addClickCommand(onClick.apply(entity)).build(),
                        CommandManager.componentBuilder().setText("§5" + (int) location.getZ())
                                .addClickCommand(onClick.apply(entity)).build());
            });
            return true;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String[] args) {
        return new ArrayList<>();
    }
}
