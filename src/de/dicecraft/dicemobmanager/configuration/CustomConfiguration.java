package de.dicecraft.dicemobmanager.configuration;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class CustomConfiguration implements Configuration {

    private Map<ConfigFlag, Boolean> configFlags = new HashMap<>();
    private ItemDropHandler handler = drops -> drops;

    public void setConfigFlags(Map<ConfigFlag, Boolean> configFlags) {
        this.configFlags = configFlags;
    }

    @Override
    public boolean shouldCancel(ConfigFlag flag) {
        return !configFlags.getOrDefault(flag, true);
    }

    public void setHandler(@Nonnull ItemDropHandler handler) {
        this.handler = handler;
    }

    @Nonnull
    @Override
    public ItemDropHandler getItemDropHandler() {
        return handler;
    }

    @Override
    public String toString() {
        return "CustomConfiguration{" +
                "configFlags=" + configFlags +
                ", handler=" + handler +
                '}';
    }
}
