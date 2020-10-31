package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.name.CustomNameUpdater;
import de.dicecraft.dicemobmanager.entity.name.NameUpdater;

import javax.annotation.Nonnull;

public interface ProtoNamedEntity {

    /**
     * Gets the custom name of the entity.
     *
     * @return the custom name string
     */
    String getName();

    void setName(String name);

    /**
     * Gets the level of the entity.
     *
     * @return the level
     */
    int getLevel();

    void setLevel(int level);

    /**
     * Gets the name supplier.
     * <p>
     * Used to create the displayed name string
     * for the entity. The default name supplier
     * {@link CustomNameUpdater} is used if its not overridden.
     *
     * @return the name supplier
     */
    @Nonnull
    default NameUpdater getNameUpdater() {
        return new CustomNameUpdater(this);
    }

}
