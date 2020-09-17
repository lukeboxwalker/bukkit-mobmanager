package de.dicecraft.dicemobmanager.entity.builder;

import de.dicecraft.dicemobmanager.entity.name.CustomNameSupplier;
import de.dicecraft.dicemobmanager.entity.name.NameSupplier;

import javax.annotation.Nonnull;

public interface ProtoNamedEntity {

    /**
     * Gets the custom name of the entity.
     *
     * @return the custom name string
     */
    default String getName() {
        return "";
    }

    /**
     * Gets the level of the entity.
     * The default level is 1
     *
     * @return the level
     */
    default int getLevel() {
        return 1;
    }

    /**
     * Gets the name supplier.
     * <p>
     * Used to create the displayed name string
     * for the entity. The default name supplier
     * {@link CustomNameSupplier} is used if its not overridden.
     *
     * @return the name supplier
     */
    @Nonnull
    default NameSupplier getNameSupplier() {
        return new CustomNameSupplier();
    }

}
