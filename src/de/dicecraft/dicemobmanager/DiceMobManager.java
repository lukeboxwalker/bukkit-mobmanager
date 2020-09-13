package de.dicecraft.dicemobmanager;

import de.dicecraft.dicemobmanager.command.CommandManager;

import de.dicecraft.dicemobmanager.entity.EntityEventListener;
import de.dicecraft.dicemobmanager.entity.event.CustomEventManager;
import de.dicecraft.dicemobmanager.entity.event.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public class DiceMobManager extends JavaPlugin {

    // singleton instance of the plugin
    private static DiceMobManager INSTANCE;
    private static EventManager EVENT_MANAGER;

    @Override
    public void onEnable() {
        super.onEnable();
        INSTANCE = this;
        EVENT_MANAGER = new CustomEventManager(this);
        Bukkit.getPluginManager().registerEvents(new EntityEventListener(), this);
        CommandManager.registerCommands(this);

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static DiceMobManager getInstance() {
        return INSTANCE;
    }

    public static EventManager getEventManager() {
        return EVENT_MANAGER;
    }
}
