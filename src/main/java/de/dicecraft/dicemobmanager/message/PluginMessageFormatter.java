package de.dicecraft.dicemobmanager.message;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class PluginMessageFormatter implements MessageFormatter {

    private static final char OPEN = '{';
    private static final char CLOSE = '}';
    private static final String PREFIX = "§8[§aDiceMobManager§8] ";


    @Override
    public void sendMessage(final CommandSender sender, final String msg, final Object... params) {
        sendMessage(sender, msg, this::processObjects, params);
    }

    /**
     * Sends a message to a CommandSender.
     *
     * @param sender   the sender that receives the message
     * @param msg      the string message
     * @param function to process the msg and calculate the next index.
     * @param params   the params to format into the message
     * @param <T>      the type of params.
     */
    @SafeVarargs
    public final <T> void sendMessage(final CommandSender sender, final String msg,
                                      final Function<T> function, final T... params) {
        final String resultMsg = PREFIX + msg;
        final List<BaseComponent> components = new ArrayList<>();
        int start = 0;
        int index = 0;
        while (index < resultMsg.length()) {
            final char current = resultMsg.charAt(index);
            if (current == OPEN) {
                components.add(new TextComponent(resultMsg.substring(start, index)));
                index += function.apply(resultMsg, index, components, params);
                start = index;
            }
            index++;
        }
        if (start != resultMsg.length() - 1) {
            components.add(new TextComponent(resultMsg.substring(start)));
        }
        final TextComponent textComponent = new TextComponent();
        textComponent.setExtra(components);
        sender.sendMessage(textComponent);
    }

    @Override
    public void sendMessage(final CommandSender sender, final String msg, final TextComponent... params) {
        sendMessage(sender, msg, this::processComponents, params);
    }

    private int processObjects(final String msg, final int index,
                               final List<BaseComponent> components, final Object... params) {
        for (int i = index; i < msg.length(); i++) {
            final char current = msg.charAt(i);
            if (!Character.isDigit(current) && current == CLOSE) {
                final int replaceIndex = Integer.parseInt(msg.substring(index + 1, i));
                if (replaceIndex < params.length) {
                    components.add(new TextComponent("§5" + params[replaceIndex].toString()));
                    return i - index + 1;
                }
            }
        }
        return 0;
    }

    private int processComponents(final String msg, final int index, final List<BaseComponent> components,
                                  final TextComponent... params) {
        for (int i = index; i < msg.length(); i++) {
            final char current = msg.charAt(i);
            if (!Character.isDigit(current) && current == '}') {
                final int replaceIndex = Integer.parseInt(msg.substring(index + 1, i));
                if (replaceIndex < params.length) {
                    components.add(params[replaceIndex]);
                    return i - index + 1;
                }
            }
        }
        return 0;
    }

    private interface Function<T> {
        int apply(String msg, int index, List<BaseComponent> components, T... params);
    }
}
