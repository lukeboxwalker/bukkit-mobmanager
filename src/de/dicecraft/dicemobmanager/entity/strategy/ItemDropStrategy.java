package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;

public interface ItemDropStrategy extends Strategy {

    void play(ItemDropEvent dropEvent);
}
