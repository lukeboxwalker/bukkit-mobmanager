package de.dicecraft.dicemobmanager.configuration;

import javax.annotation.Nonnull;

public interface ConfigBuilder {

    ConfigBuilder setBooleanFlag(ConfigFlag configFlag, boolean flag);

    ConfigBuilder allowFlag(ConfigFlag configFlag);

    ConfigBuilder allowAllFlags();

    ConfigBuilder denyFlag(ConfigFlag configFlag);

    ConfigBuilder denyAllFlags();

    ConfigBuilder setItemDropHandler(@Nonnull ItemDropHandler itemDropHandler);

    Configuration build();
}
