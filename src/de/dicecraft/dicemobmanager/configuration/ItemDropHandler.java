package de.dicecraft.dicemobmanager.configuration;

import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;

import java.util.List;

public interface ItemDropHandler {

    /**
     * Handles the DeathDrops.
     * <p>
     * All drops that are passed to this method are
     * about to be dropped. The handler is able to
     * decide which items will drop after all.
     *
     * @param drops the death drops that are about to drop.
     * @return the actual drops that will drop when entity dies.
     */
    List<DeathDrop> handleDrops(final List<DeathDrop> drops);
}
