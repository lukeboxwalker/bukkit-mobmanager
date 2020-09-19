package de.dicecraft.dicemobmanager.configuration;

import java.util.HashMap;
import java.util.Map;

public class CustomConfigBuilder implements ConfigBuilder {

    private final Map<ConfigFlag, Boolean> configFlags = new HashMap<>();

    @Override
    public ConfigBuilder setBooleanFlag(ConfigFlag configFlag, boolean flag) {
        configFlags.put(configFlag, flag);
        return this;
    }

    @Override
    public Configuration build() {
        CustomConfiguration configuration = new CustomConfiguration();
        configuration.setConfigFlags(configFlags);
        return configuration;
    }
}
