package de.dicecraft.dicemobmanager;

import de.dicecraft.dicemobmanager.command.CommandManager;

import org.bukkit.plugin.java.JavaPlugin;


public class DiceMobManager extends JavaPlugin {

    // singleton instance of the plugin
    private static DiceMobManager INSTANCE;

    @Override
    public void onEnable() {
        super.onEnable();
        CommandManager.registerCommands(this);
        INSTANCE = this;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static DiceMobManager getInstance() {
        return INSTANCE;
    }
}
