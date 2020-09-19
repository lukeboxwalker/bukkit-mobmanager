package de.dicecraft.dicemobmanager.configuration;

import java.util.HashMap;
import java.util.Map;

public class CustomConfiguration implements Configuration {

    private Map<ConfigFlag, Boolean> configFlags = new HashMap<>();

    public Map<ConfigFlag, Boolean> getConfigFlags() {
        return configFlags;
    }

    public void setConfigFlags(Map<ConfigFlag, Boolean> configFlags) {
        this.configFlags = configFlags;
    }

    @Override
    public boolean canSlimeSplit() {
        return configFlags.getOrDefault(ConfigFlag.SLIME_SPLIT, true);
    }

    @Override
    public boolean canDestroyBlock() {
        return configFlags.getOrDefault(ConfigFlag.DESTROY_BLOCK, true);
    }
}
