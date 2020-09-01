package de.dicecraft.dicemobmanager.utils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class ReflectionHelper {

    /**
     * Reflecting static field of a given class.
     * <p>
     * Accesses a static field by name using java reflection api {@link Field}.
     * Field need to be static, there is no object given to operate on.
     *
     * @param objectClass the class which contains the field variable
     * @param tClass      the class of the field
     * @param name        of the field
     * @param <T>         the type of the field
     * @return the static field
     */
    @Nullable
    public static <T> T getEnumField(final Class<?> objectClass, final Class<T> tClass, final String name) {
        try {
            Field field = objectClass.getDeclaredField(name);
            field.setAccessible(true);
            return tClass.cast(field.get(null));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    @Nullable
    public static <T> T getEnumField(final Class<T> tClass, final String name) {
        return getEnumField(tClass, tClass, name);
    }
}
