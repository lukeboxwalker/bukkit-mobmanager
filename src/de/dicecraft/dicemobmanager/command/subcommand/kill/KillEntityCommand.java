package de.dicecraft.dicemobmanager.command.subcommand.kill;

import de.dicecraft.dicemobmanager.command.BaseCommand;
import de.dicecraft.dicemobmanager.command.subcommand.kill.subcommand.KillAllCommand;
import de.dicecraft.dicemobmanager.command.subcommand.kill.subcommand.KillRadiusCommand;
import de.dicecraft.dicemobmanager.entity.EntityManager;

public class KillEntityCommand extends BaseCommand {

    public KillEntityCommand(EntityManager manager) {
        super("kill", new KillAllCommand(manager), new KillRadiusCommand(manager));
    }
}
