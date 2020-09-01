package de.dicecraft.dicemobmanager.entity.pathfinder.navigation;

/**
 * Wrapping nms path entity.
 * <p>
 * Used to hide nms implementation from
 * custom pathfinder goals or goal targets
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public interface CustomPathEntity {

    /**
     * Indicates if path entity is present.
     * <p>
     * The path entity will be modified by the
     * specific navigation
     *
     * @return if the path is present
     */
    boolean hasPath();
}
