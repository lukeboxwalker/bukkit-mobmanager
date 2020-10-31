package de.dicecraft.dicemobmanager.command.subcommand.kill.subcommand;

import de.dicecraft.dicemobmanager.command.Command;
import de.dicecraft.dicemobmanager.command.CommandUtils;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KillAllCommand implements Command {

    private static final String SILENT = "-s";
    private static final String KILLED_ALL = "§7All entities killed. Total count §5{0}§7.";
    private static final String NO_DROPS = " Entities didn't drop there loot!";

    private final EntityManager manager;

    public KillAllCommand(final EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "-a";
    }

    @Override
    public boolean execute(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        if (args.length == 0) {
            final Map<Entity, ProtoEntity<?>> entities = manager.getAllEntities();
            entities.forEach((entity, protoEntity) -> {
                final LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(livingEntity.getHealth() * 2);
            });
            CommandUtils.messageFormatter().sendMessage(sender, KILLED_ALL, entities.size());


            return true;
        } else if (args.length == 1 && args[0].equals(SILENT)) {
            final Map<Entity, ProtoEntity<?>> entities = manager.getAllEntities();
            manager.setItemsDrop(false);
            entities.forEach((entity, protoEntity) -> {
                final LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(livingEntity.getHealth() * 2);
            });
            manager.setItemsDrop(true);
            CommandUtils.messageFormatter().sendMessage(sender, KILLED_ALL + NO_DROPS, entities.size());
            return true;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        if (args.length == 1 && SILENT.startsWith(args[0])) {
            return Collections.singletonList(SILENT);
        }
        return new ArrayList<>();
    }
}
