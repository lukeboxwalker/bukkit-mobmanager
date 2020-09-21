package de.dicecraft.dicemobmanager.message;

import org.bukkit.command.CommandSender;

public class PluginMessageFormatter implements MessageFormatter {

    private static final String PREFIX = "§8[§aDiceMobManager§8] ";

    @Override
    public void sendMessage(CommandSender sender, String msg, Object... params) {
        String resultMsg = PREFIX + msg;
        for (int i = 0; i < params.length; i++) {
            resultMsg = resultMsg.replace("{" + i + "}", params[i].toString());
        }
        sender.sendMessage(resultMsg);
    }
}
