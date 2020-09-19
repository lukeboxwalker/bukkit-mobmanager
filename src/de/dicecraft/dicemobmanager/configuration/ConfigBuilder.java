package de.dicecraft.dicemobmanager.configuration;

public interface ConfigBuilder {

    ConfigBuilder setBooleanFlag(ConfigFlag configFlag, boolean flag);

    Configuration build();
}
