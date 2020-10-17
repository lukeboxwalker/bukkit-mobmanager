package de.dicecraft.dicemobmanager.message;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PluginMessageFormatter implements MessageFormatter {

    private static final String PREFIX = "§8[§aDiceMobManager§8] ";


    @Override
    public void sendMessage(CommandSender sender, String msg, Object... params) {
        sendMessage(sender, msg, this::processObjects, params);
    }

    public <T> void sendMessage(CommandSender sender, String msg, Consumer<T> consumer, T... params) {
        String resultMsg = PREFIX + msg;
        List<BaseComponent> components = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < resultMsg.length(); i++) {
            char current = resultMsg.charAt(i);
            if (current == '{') {
                components.add(new TextComponent(resultMsg.substring(start, i)));
                i += consumer.apply(resultMsg, i, components, params);
                start = i;
            }
        }
        if (start != resultMsg.length() - 1) {
            components.add(new TextComponent(resultMsg.substring(start)));
        }
        TextComponent textComponent = new TextComponent();
        textComponent.setExtra(components);
        sender.sendMessage(textComponent);
    }

    @Override
    public void sendMessage(CommandSender sender, String msg, TextComponent... params) {
        sendMessage(sender, msg, this::processComponents, params);
    }

    private int processObjects(String msg, int index, List<BaseComponent> components, Object... params) {
        for (int i = index; i < msg.length(); i++) {
            char current = msg.charAt(i);
            if (!Character.isDigit(current) && current == '}') {
                int replaceIndex = Integer.parseInt(msg.substring(index + 1, i));
                if (replaceIndex < params.length) {
                    components.add(new TextComponent("§5" + params[replaceIndex].toString()));
                    return i - index + 1;
                }
            }
        }
        return 0;
    }

    private int processComponents(String msg, int index, List<BaseComponent> components, TextComponent... params) {
        for (int i = index; i < msg.length(); i++) {
            char current = msg.charAt(i);
            if (!Character.isDigit(current) && current == '}') {
                int replaceIndex = Integer.parseInt(msg.substring(index + 1, i));
                if (replaceIndex < params.length) {
                    components.add(params[replaceIndex]);
                    return i - index + 1;
                }
            }
        }
        return 0;
    }

    interface Consumer<T> {
        int apply(String msg, int index, List<BaseComponent> components, T... params);
    }
}
