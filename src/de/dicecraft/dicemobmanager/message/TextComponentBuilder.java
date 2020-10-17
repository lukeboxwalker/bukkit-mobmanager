package de.dicecraft.dicemobmanager.message;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;

import static net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT;

public class TextComponentBuilder {

    private String text;
    private String click;
    private ClickEvent.Action action;
    private ChatColor color = ChatColor.WHITE;

    public TextComponentBuilder() {
    }

    public TextComponentBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public TextComponentBuilder setColor(ChatColor color) {
        this.color = color;
        return this;
    }

    public TextComponentBuilder addClickEvent(ClickEvent.Action clickEventAction, String value) {
        this.action = clickEventAction;
        this.click = value;
        return this;
    }

    public TextComponentBuilder addClickCommand(RunnableCommand runnableCommand) {
        this.action = ClickEvent.Action.RUN_COMMAND;
        this.click = runnableCommand.runCommand();
        return this;
    }

    public TextComponent build() {
        TextComponent textComponent = new TextComponent();
        textComponent.setText(ChatColor.COLOR_CHAR + color.getChar() + this.text);
        if (this.click != null && (this.action != null)) {
            textComponent.setClickEvent(new ClickEvent(action, this.click));
            textComponent.setHoverEvent(new HoverEvent(SHOW_TEXT, new Text(this.click)));
        }
        return textComponent;
    }

    public interface RunnableCommand {
        String runCommand();
    }
}
