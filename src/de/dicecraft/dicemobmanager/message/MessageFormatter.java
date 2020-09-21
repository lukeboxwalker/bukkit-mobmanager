package de.dicecraft.dicemobmanager.message;

import org.bukkit.command.CommandSender;

public interface MessageFormatter {

    void sendMessage(CommandSender sender, String msg, Object... params);
}
