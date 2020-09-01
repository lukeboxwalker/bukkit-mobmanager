package de.dicecraft.dicemobmanager;

import de.dicecraft.dicemobmanager.adapter.CustomEntityFactory;
import de.dicecraft.dicemobmanager.command.CommandManager;
import de.dicecraft.dicemobmanager.entity.builder.CustomEntityBuilder;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * DiceMobManager main class.
 * <p>
 * The mob manager is able to create custom entities by
 * wrapping nms entities and their according classes.
 * <p>
 * An entity can be configured using a {@link CustomEntityBuilder}
 * or directly via the {@link CustomEntityFactory}
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class DiceMobManager extends JavaPlugin {

    // singleton instance of the plugin
    private static DiceMobManager INSTANCE;

    @Override
    public void onEnable() {
        super.onEnable();
        INSTANCE = this;
        CommandManager.registerCommands(this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static DiceMobManager getInstance() {
        return INSTANCE;
    }
}
