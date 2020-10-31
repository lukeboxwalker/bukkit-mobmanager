package de.dicecraft.dicemobmanager.command;

import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TestCommand implements Command {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public boolean execute(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        return true;
    }

    @Override
    public List<String> tabComplete(final @Nonnull CommandSender sender, final @Nonnull String[] args) {
        return new ArrayList<>();
    }
}
