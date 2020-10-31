package de.dicecraft.dicemobmanager.configuration;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomConfigBuilder implements ConfigBuilder {

    private final Map<ConfigFlag, Boolean> configFlags = new HashMap<>();
    private ItemDropHandler itemDropHandler = drops -> drops;

    @Override
    public ConfigBuilder setBooleanFlag(final ConfigFlag configFlag, final boolean flag) {
        configFlags.put(configFlag, flag);
        return this;
    }

    @Override
    public ConfigBuilder allowFlag(final ConfigFlag configFlag) {
        configFlags.put(configFlag, true);
        return this;
    }

    @Override
    public ConfigBuilder allowAllFlags() {
        Arrays.stream(ConfigFlag.values()).forEach(flag -> configFlags.put(flag, true));
        return this;
    }

    @Override
    public ConfigBuilder denyFlag(final ConfigFlag configFlag) {
        configFlags.put(configFlag, false);
        return this;
    }

    @Override
    public ConfigBuilder denyAllFlags() {
        Arrays.stream(ConfigFlag.values()).forEach(flag -> configFlags.put(flag, false));
        return this;
    }

    @Override
    public ConfigBuilder setItemDropHandler(final @Nonnull ItemDropHandler itemDropHandler) {
        this.itemDropHandler = itemDropHandler;
        return this;
    }

    @Override
    public Configuration build() {
        final CustomConfiguration configuration = new CustomConfiguration();
        configuration.setConfigFlags(configFlags);
        configuration.setHandler(itemDropHandler);
        return configuration;
    }
}
