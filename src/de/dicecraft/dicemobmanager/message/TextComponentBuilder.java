package de.dicecraft.dicemobmanager.message;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;

public class TextComponentBuilder {

    private String text;
    private String click;
    private ClickEvent.Action action;
    private ChatColor color = ChatColor.WHITE;

    public TextComponentBuilder setText(final String text) {
        this.text = text;
        return this;
    }

    public TextComponentBuilder setColor(final ChatColor color) {
        this.color = color;
        return this;
    }

    /**
     * Adding a click event to the text.
     *
     * @param clickEventAction the click event action.
     * @param value            the value when clicking
     * @return this builder to continue.
     */
    public TextComponentBuilder addClickEvent(final ClickEvent.Action clickEventAction, final String value) {
        this.action = clickEventAction;
        this.click = value;
        return this;
    }

    /**
     * Adding a click event to the text that performs a command.
     *
     * @param runnableCommand the runnable that returns the command string.
     * @return this builder to continue.
     */
    public TextComponentBuilder addClickCommand(final RunnableCommand runnableCommand) {
        this.action = ClickEvent.Action.RUN_COMMAND;
        this.click = runnableCommand.runCommand();
        return this;
    }

    /**
     * Builds the TextComponent.
     * <p>
     * Setting the text as well as color, click events and hover events.
     *
     * @return the new TextComponent.
     */
    public TextComponent build() {
        final TextComponent textComponent = new TextComponent();
        textComponent.setText(ChatColor.COLOR_CHAR + color.getChar() + this.text);
        if (this.click != null && this.action != null) {
            textComponent.setClickEvent(new ClickEvent(action, this.click));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§b" + this.click)));
        }
        return textComponent;
    }

    public interface RunnableCommand {
        String runCommand();
    }
}
