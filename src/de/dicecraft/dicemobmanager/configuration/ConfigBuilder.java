package de.dicecraft.dicemobmanager.configuration;

import javax.annotation.Nonnull;

public interface ConfigBuilder {

    ConfigBuilder setBooleanFlag(ConfigFlag configFlag, boolean flag);

    ConfigBuilder setItemDropHandler(@Nonnull ItemDropHandler itemDropHandler);

    Configuration build();
}
