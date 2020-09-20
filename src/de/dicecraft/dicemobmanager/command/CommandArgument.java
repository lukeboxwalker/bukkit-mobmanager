package de.dicecraft.dicemobmanager.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CommandArgument implements AliasArgument {

    private final List<String> aliases;

    public CommandArgument(final String name, final String... aliases) {
        this.aliases = new ArrayList<>();
        this.aliases.add(name);
        this.aliases.addAll(Arrays.asList(aliases));

    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }
}
