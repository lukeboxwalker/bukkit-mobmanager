package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.event.SlimeEvent;

public interface ProtoSlime {

    default void onSlimeSplit(SlimeEvent event) {
    }
}
