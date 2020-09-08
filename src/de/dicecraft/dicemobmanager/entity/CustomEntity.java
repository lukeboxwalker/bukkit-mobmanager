package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.builder.CustomEntityBuilder;
import de.dicecraft.dicemobmanager.entity.builder.EntityBuilder;

import de.dicecraft.dicemobmanager.entity.datawatcher.CustomDataWatcher;
import net.minecraft.server.v1_16_R2.ChatComponentText;
import net.minecraft.server.v1_16_R2.DamageSource;
import net.minecraft.server.v1_16_R2.DataWatcher;
import net.minecraft.server.v1_16_R2.EntityCreature;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.GenericAttributes;
import net.minecraft.server.v1_16_R2.SoundEffect;
import net.minecraft.server.v1_16_R2.World;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.Set;

public class CustomEntity extends CustomEntityMoving implements ICustomEntity {

    private static final String DEFAULT_NAME = "CustomEntity";
    private static final int DEFAULT_LEVEL = 1;

    private CustomNameBuilder nameBuilder;
    private CustomColor nameColor;
    private SoundPalette<SoundEffect> soundPalette;
    private String customNameTag;
    private int customLevel;

    private DamageBehavior damageBehavior;
    private BaseTickBehavior baseTickBehavior;
    private MovementTickBehavior movementTickBehavior;
    private MobTickBehavior mobTickBehavior;

    protected CustomEntity(final EntityTypes<? extends EntityCreature> entityTypes, final World world) {
        super(entityTypes, world);
        this.nameBuilder = new NameBuilder();
        this.nameColor = CustomColor.LIGHT_RED;
        this.customNameTag = DEFAULT_NAME;
        this.customLevel = DEFAULT_LEVEL;
        this.damageBehavior = customDamage -> {};
        this.baseTickBehavior = entity -> {};
        this.movementTickBehavior = entity -> {};
        this.mobTickBehavior = entity -> {};
    }

    public void registerDataWatchers(final Set<CustomDataWatcher> dataObjects) {
        try {
            final Field field = DataWatcher.class.getDeclaredField("registrationLocked");
            field.setAccessible(true);
            field.setBoolean(this.datawatcher, false);
            dataObjects.forEach(dataObject -> dataObject.install(this.datawatcher));
            field.setBoolean(this.datawatcher, true);
        } catch (IllegalAccessException | NoSuchFieldException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }
    }

    private void updateName() {
        int maxHealth = (int) Objects.requireNonNull(this.getAttributeInstance(GenericAttributes.MAX_HEALTH)).getValue();
        ChatComponentText component = new ChatComponentText(nameBuilder
                .hasName(customNameTag)
                .hasLevel(customLevel)
                .hasNameColor(nameColor)
                .hasCurrentHealth((int) getHealth())
                .hasMaxHealth(maxHealth).build());
        setCustomName(component);
    }

    @Override
    public void setNameColor(CustomColor nameColor) {
        if (nameColor == null) return;
        this.nameColor = nameColor;
    }

    @Override
    public void setNameBuilder(CustomNameBuilder customNameBuilder) {
        if (customNameBuilder == null) return;
        this.nameBuilder = customNameBuilder;
    }

    @Override
    public void setCustomNameTag(String customNameTag) {
        if (customNameTag == null) return;
        this.customNameTag = customNameTag;
    }

    @Override
    public void setCustomLevel(int customLevel) {
        this.customLevel = customLevel;
    }

    @Override
    public void setDamageBehavior(DamageBehavior damageBehavior) {
        if (damageBehavior == null) return;
        this.damageBehavior = damageBehavior;
    }

    @Override
    public void setBaseTickBehavior(BaseTickBehavior baseTickBehavior) {
        if (baseTickBehavior == null) return;
        this.baseTickBehavior = baseTickBehavior;
    }

    @Override
    public void setMovementTickBehavior(MovementTickBehavior movementTickBehavior) {
        if (movementTickBehavior == null) return;
        this.movementTickBehavior = movementTickBehavior;
    }

    @Override
    public void setMobTickBehavior(MobTickBehavior mobTickBehavior) {
        if (mobTickBehavior == null) return;
        this.mobTickBehavior = mobTickBehavior;
    }

    @Override
    public void setSoundPalette(SoundPalette<SoundEffect> soundPalette) {
        if (soundPalette == null) return;
        this.soundPalette = soundPalette;
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        this.updateName();
        super.setCustomNameVisible(visible);
    }

    @Override
    public void tick() {
        super.tick();
        baseTickBehavior.onEntityBaseTick(this.getBukkitEntity());
    }

    @Override
    public void movementTick() {
        super.movementTick();
        movementTickBehavior.onEntityMovementTick(this.getBukkitEntity());
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        mobTickBehavior.onEntityMobTick(this.getBukkitMob());
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float damage) {
        final Damage customDamage = new Damage(damageSource, damage);
        damageBehavior.onEntityDamage(customDamage);
        boolean success = super.damageEntity(customDamage.getDamageSource(), customDamage.getDamage());
        if (success) {
            this.updateName();
        }
        return success;
    }

    @Override
    protected final SoundEffect getSoundAmbient() {
        return soundPalette.getAmbient();
    }

    @Override
    protected final SoundEffect getSoundHurt(DamageSource damagesource) {
        return soundPalette.getHurt();
    }

    @Override
    protected final SoundEffect getSoundDeath() {
        return soundPalette.getDeath();
    }

    public static CustomEntityBuilder<CustomEntity> builder() {
        return new EntityBuilder<>();
    }
}
