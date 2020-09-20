package de.dicecraft.dicemobmanager.command.subcommand.kill.subcommand;

import de.dicecraft.dicemobmanager.command.Command;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KillAllCommand implements Command {

    private final EntityManager manager;
    private final String SILENT = "-s";

    public KillAllCommand(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public String getName() {
        return "-a";
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length == 0) {
            Map<Entity, ProtoEntity> entities = manager.getAllEntities();
            entities.forEach((entity, protoEntity) -> {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(livingEntity.getHealth() * 2);
            });
            sender.sendMessage("§8[§aDiceMobManager§8] §7All entities killed. Total count §5" +
                    entities.size() + "§7.");
            return true;
        } else if (args.length == 1 && args[0].equals(SILENT)) {
            Map<Entity, ProtoEntity> entities = manager.getAllEntities();
            manager.setItemsDrop(false);
            entities.forEach((entity, protoEntity) -> {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(livingEntity.getHealth() * 2);
            });
            manager.setItemsDrop(true);
            sender.sendMessage("§8[§aDiceMobManager§8] §7All entities killed. Total count §5" +
                    entities.size() + "§7. Entities didn't drop there loot!");
            return true;
        }
        return false;
    }

    @Override
    public List<String> tabComplete(@Nonnull CommandSender sender, @Nonnull String[] args) {
        if (args.length == 1 && SILENT.startsWith(args[0])) {
            return Collections.singletonList(SILENT);
        }
        return new ArrayList<>();
    }
}
