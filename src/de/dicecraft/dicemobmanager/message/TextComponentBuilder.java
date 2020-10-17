package de.dicecraft.dicemobmanager.message;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentBuilder {

    private String text;
    private String click;
    private ClickEvent.Action action;

    public TextComponentBuilder() {
    }

    public TextComponentBuilder setText(String text) {
        this.text = text;
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
        textComponent.setText(this.text);
        if (this.click != null && (this.action != null)) {
            textComponent.setClickEvent(new ClickEvent(action, this.click));
        }
        return textComponent;
    }

    public interface RunnableCommand {
        String runCommand();
    }
}
