package de.dicecraft.dicemobmanager.adapter;

import de.dicecraft.dicemobmanager.entity.CustomEntity;
import de.dicecraft.dicemobmanager.entity.CustomType;
import de.dicecraft.dicemobmanager.entity.CustomNameBuilder;
import de.dicecraft.dicemobmanager.entity.builder.Attribute;
import de.dicecraft.dicemobmanager.entity.builder.EntityConfiguration;
import de.dicecraft.dicemobmanager.entity.SoundPalette;

import de.dicecraft.dicemobmanager.entity.builder.PriorityEntry;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoal;

import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoalTarget;
import de.dicecraft.dicemobmanager.utils.ReflectionHelper;
import it.unimi.dsi.fastutil.Function;
import net.minecraft.server.v1_16_R2.AttributeBase;
import net.minecraft.server.v1_16_R2.AttributeMapBase;
import net.minecraft.server.v1_16_R2.AttributeProvider;
import net.minecraft.server.v1_16_R2.EntityLiving;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.GenericAttributes;
import net.minecraft.server.v1_16_R2.SoundEffect;
import net.minecraft.server.v1_16_R2.SoundEffects;
import net.minecraft.server.v1_16_R2.World;

import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R2.attribute.CraftAttributeMap;

import org.bukkit.Location;
import org.bukkit.event.entity.CreatureSpawnEvent;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Creates and spawns {@link CustomEntity}.
 * <p>
 * Using NMS and reflections to wrap the original
 * minecraft entities.
 * For Entity configuration also see {@link EntityConfiguration}.
 *
 * @author Walkehorst Lukas
 * @since 1.0
 */
public class NMS_EntityFactory_v1_16_R2 implements CustomEntityFactory {

    // field names from EntityLiving
    private static final String ATTRIBUTE_FIELD = "attributeMap";
    private static final String CRAFT_ATTRIBUTE_FIELD = "craftAttributes";
    // formatting the entity name to match nms enum name
    private static final Function<String, String> nameFormatter = (string) -> "ENTITY_" + string.toString().toUpperCase() + "_";

    /**
     * Creates a {@link CustomEntity}.
     * <p>
     * Entity is created by using a valid {@link EntityConfiguration} which
     * consists all information for the entity. The configuration is used to identify the
     * CustomType of the entity and to apply all strategies and behaviors the entity should
     * consist of.
     * <p>
     * Sound:
     * The entity's sound is specified by their {@link SoundEffect}, therefor the CustomEntity
     * is provided with a replaceable {@link SoundPalette} containing the sounds ambient, death, hurt
     * and step.
     * <p>
     * Goal and target selectors:
     * The entity's goal and target selection is specified by their {@link CustomPathfinderGoal}
     * and {@link CustomPathfinderGoalTarget}, which will be added to the entity
     * considering the given priority.
     * <p>
     * Generic attributes:
     * The attributes will be set according to the given value Map {@link EntityConfiguration#getAttributes()}.
     * If the attribute is negative the default value specified in {@link AttributeBase} is used.
     * <p>
     * Custom name tag:
     * The entity's custom name is set to visible and is provided by the specified {@link CustomNameBuilder}
     *
     * @param configuration to configure the entity
     * @param <T>           the specific type of the CustomEntity if needed (in most cases this is {@link CustomEntity})
     * @return the CustomEntity that was created
     * @throws EntityCreationException when entity creation failed
     */
    @Override
    public <T extends CustomEntity> T createCustomEntity(final EntityConfiguration<T> configuration) throws EntityCreationException {
        final World world = ((CraftWorld) configuration.getWorld()).getHandle();
        final CustomType<T> customType = configuration.getEntityType();
        try {
            EntityTypes<?> entityTypes = ReflectionHelper.getEnumField(EntityTypes.class, EntityTypes.class, customType.getName().toUpperCase());
            Constructor<? extends T> constructor = customType.getEntityClass().getDeclaredConstructor(EntityTypes.class, World.class);
            constructor.setAccessible(true);
            T customEntity = constructor.newInstance(entityTypes, world);

            // setting sound effects
            final SoundPalette<SoundEffect> soundPalette = new SoundPalette<>();
            final String name = nameFormatter.apply(customType.getName());
            soundPalette.setAmbient(ReflectionHelper.getEnumField(SoundEffects.class, SoundEffect.class, name + SoundPalette.AMBIENT));
            soundPalette.setDeath(ReflectionHelper.getEnumField(SoundEffects.class, SoundEffect.class, name + SoundPalette.DEATH));
            soundPalette.setHurt(ReflectionHelper.getEnumField(SoundEffects.class, SoundEffect.class, name + SoundPalette.HURT));
            soundPalette.setStep(ReflectionHelper.getEnumField(SoundEffects.class, SoundEffect.class, name + SoundPalette.STEP));
            customEntity.setSoundPalette(soundPalette);

            // setting goal selectors
            for (PriorityEntry<Supplier<CustomPathfinderGoal>> priorityEntry : configuration.getPathfinderGoals()) {
                customEntity.addPathfinderGoal(priorityEntry.getPriority(), priorityEntry.getEntry().get());
            }
            // setting target selectors
            for (PriorityEntry<Supplier<CustomPathfinderGoalTarget>> priorityEntry : configuration.getPathfinderTargets()) {
                customEntity.addPathfinderTarget(priorityEntry.getPriority(), priorityEntry.getEntry().get());
            }

            // setting attributes
            Map<Attribute, Double> attributes = configuration.getAttributes();
            AttributeProvider.Builder builder = new AttributeProvider.Builder();
            attributes.forEach(((attribute, value) -> {
                if (value < 0) {
                    builder.a(ReflectionHelper.getEnumField(GenericAttributes.class, AttributeBase.class, attribute.name().toUpperCase()));
                } else {
                    builder.a(ReflectionHelper.getEnumField(GenericAttributes.class, AttributeBase.class, attribute.name().toUpperCase()), value);
                }
            }));

            final AttributeMapBase attributeMap = new AttributeMapBase(builder.a());
            Field field = EntityLiving.class.getDeclaredField(ATTRIBUTE_FIELD);
            setFinalField(field, attributeMap, customEntity);

            final CraftAttributeMap craftAttributeMap = new CraftAttributeMap(attributeMap);
            field = EntityLiving.class.getDeclaredField(CRAFT_ATTRIBUTE_FIELD);
            setFinalField(field, craftAttributeMap, customEntity);

            customEntity.setHealth(attributes.get(Attribute.MAX_HEALTH).floatValue());
            customEntity.setCustomNameVisible(true);

            // data watchers
            customEntity.registerDataWatchers(configuration.getDataWatchers());

            return customEntity;
        } catch (InstantiationException | NoSuchMethodException cause) {
            throw new EntityCreationException("Constructor of entity class did not match expected signature", cause);
        } catch (IllegalAccessException | InvocationTargetException cause) {
            throw new EntityCreationException("Could not access a reflection", cause);
        } catch (NoSuchFieldException cause) {
            throw new EntityCreationException("No such field found", cause);
        }
    }

    /**
     * Change the value of a final field.
     * <p>
     * Using java reflections to get access to final
     * field and manipulate it's value.
     *
     * @param field    to manipulate (final)
     * @param newValue to update the field
     * @param object   that contains the field
     * @throws EntityCreationException if field cant be modified
     */
    private static void setFinalField(final Field field, final Object newValue, final Object object) throws EntityCreationException {
        try {
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(object, newValue);
        } catch (NoSuchFieldException cause) {
            throw new EntityCreationException("Modifier field not found", cause);
        } catch (IllegalAccessException cause) {
            throw new EntityCreationException("Could not access final field", cause);
        }

    }

    /**
     * Spawns a {@link CustomEntity} at a given location.
     * <p>
     * Entity is spawned by using the {@link World} given inside of
     * the location. Entity is always spawned with {@link CreatureSpawnEvent.SpawnReason#CUSTOM}.
     *
     * @param customEntity the entity to spawn
     * @param location     to define where to spawn the entity
     */
    @Override
    public void spawnCustomEntity(final CustomEntity customEntity, final Location location) {
        customEntity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        final World worldServer = ((CraftWorld) location.getWorld()).getHandle();
        worldServer.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    /**
     * Creates and spawns a {@link CustomEntity} at a given location.
     * <p>
     * Entity is created by using {@link CustomEntityFactory#createCustomEntity(EntityConfiguration)}.
     * Entity is spawned by using {@link CustomEntityFactory#spawnCustomEntity(CustomEntity, Location)}.
     *
     * @param configuration to configure the entity
     * @param location      to define where to spawn the entity
     * @throws EntityCreationException when entity creation failed
     */
    @Override
    public <T extends CustomEntity> T spawnCustomEntity(EntityConfiguration<T> configuration, final Location location) throws EntityCreationException {
        final T customEntity = createCustomEntity(configuration);
        spawnCustomEntity(customEntity, location);
        return customEntity;
    }
}
