package de.dicecraft.dicemobmanager.configuration;

import java.util.HashMap;
import java.util.Map;

public class CustomConfiguration implements Configuration {

    private Map<ConfigFlag, Boolean> configFlags = new HashMap<>();

    public void setConfigFlags(Map<ConfigFlag, Boolean> configFlags) {
        this.configFlags = configFlags;
    }

    @Override
    public boolean canSlimeSplit() {
        return configFlags.getOrDefault(ConfigFlag.SLIME_SPLIT, true);
    }

    @Override
    public boolean canProjectileBlockDamage() {
        return configFlags.getOrDefault(ConfigFlag.PROJECTILE_BLOCK_DAMAGE, true);
    }

    @Override
    public boolean canCreeperBlockDamage() {
        return configFlags.getOrDefault(ConfigFlag.CREEPER_EXPLOSION_DAMAGE, true);
    }
}
