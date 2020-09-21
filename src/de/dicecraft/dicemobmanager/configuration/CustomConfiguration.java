package de.dicecraft.dicemobmanager.configuration;

import java.util.HashMap;
import java.util.Map;

public class CustomConfiguration implements Configuration {

    private Map<ConfigFlag, Boolean> configFlags = new HashMap<>();

    public void setConfigFlags(Map<ConfigFlag, Boolean> configFlags) {
        this.configFlags = configFlags;
    }

    @Override
    public boolean getBoolean(ConfigFlag flag) {
        return configFlags.getOrDefault(flag, true);
    }
}
