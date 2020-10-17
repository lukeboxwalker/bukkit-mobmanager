package de.dicecraft.dicemobmanager.message;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

public interface MessageFormatter {

    void sendMessage(CommandSender sender, String msg, TextComponent... params);

    void sendMessage(CommandSender sender, String msg, Object... params);
}
