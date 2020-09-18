package de.dicecraft.dicemobmanager.entity.strategy;

import de.dicecraft.dicemobmanager.entity.event.SlimeEvent;

public interface SlimeSplitStrategy extends Strategy {

    void play(SlimeEvent slimeEvent);
}
