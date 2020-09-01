package de.dicecraft.dicemobmanager.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomType<T extends CustomEntity> {

    public static final CustomType<CustomEntity> ZOMBIE = new CustomType<>("ZOMBIE");
    public static final CustomType<CustomEntity> ZOMBIE_VILLAGER = new CustomType<>("ZOMBIE_VILLAGER");
    public static final CustomType<CustomEntity> VILLAGER = new CustomType<>("VILLAGER");
    public static final CustomType<CustomEntity> PILLAGER = new CustomType<>("PILLAGER");
    public static final CustomType<CustomEntity> ILLUSIONER = new CustomType<>("ILLUSIONER");
    public static final CustomType<CustomEntity> DROWNED = new CustomType<>("DROWNED");
    public static final CustomType<CustomEntity> RAVAGER = new CustomType<>("RAVAGER");
    public static final CustomType<CustomEntity> SKELETON = new CustomType<>("SKELETON");
    public static final CustomType<CustomEntity> WITHER_SKELETON = new CustomType<>("WITHER_SKELETON");
    public static final CustomType<CustomEntity> WITCH = new CustomType<>("WITCH");
    public static final CustomType<CustomEntity> PIG = new CustomType<>("PIG");
    public static final CustomType<CustomEntity> SHEEP = new CustomType<>("SHEEP");
    public static final CustomType<CustomEntity> COW = new CustomType<>("COW");
    public static final CustomType<CustomEntity> RABBIT = new CustomType<>("RABBIT");
    public static final CustomType<CustomEntity> LLAMA = new CustomType<>("LLAMA");
    public static final CustomType<CustomEntity> TRADER_LLAMA = new CustomType<>("TRADER_LLAMA");
    public static final CustomType<CustomEntity> BLAZE = new CustomType<>("BLAZE");
    public static final CustomType<CustomEntity> ENDERMAN = new CustomType<>("ENDERMAN");
    public static final CustomType<CustomEntity> HORSE = new CustomType<>("HORSE");
    public static final CustomType<CustomEntity> SKELETON_HORSE = new CustomType<>("SKELETON_HORSE");
    public static final CustomType<CustomEntity> HUSK = new CustomType<>("HUSK");
    public static final CustomType<CustomEntity> STRAY = new CustomType<>("STRAY");
    public static final CustomType<CustomEntity> SPIDER = new CustomType<>("SPIDER");
    public static final CustomType<CustomEntity> CAVE_SPIDER = new CustomType<>("CAVE_SPIDER");
    public static final CustomType<CustomEntity> VEX = new CustomType<>("VEX");
    public static final CustomType<CustomEntity> WOLF = new CustomType<>("WOLF");
    public static final CustomType<CustomEntity> CREEPER = new CustomType<>("CREEPER");

    private final String name;
    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    private CustomType(final String name) {
        this(name, (Class<T>) CustomEntity.class);
    }

    private CustomType(final String name, final Class<T> entityClass) {
        this.name = name.toUpperCase();
        this.entityClass = entityClass;
    }

    @SuppressWarnings("unchecked")
    public static CustomType<CustomEntity> getByName(final String name) throws IllegalArgumentException {
        try {
            final Field field = CustomType.class.getDeclaredField(name.toUpperCase());
            field.setAccessible(true);
            return (CustomType<CustomEntity>) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static List<String> values() {
        return Arrays.stream(CustomType.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()) && field.getType().equals(CustomType.class))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        return ((CustomType<?>) field.get(null)).getName();
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                }).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
