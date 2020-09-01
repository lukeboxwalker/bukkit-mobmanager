package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.builder.CustomEntityBuilder;
import de.dicecraft.dicemobmanager.entity.builder.EntityBuilder;

import net.minecraft.server.v1_16_R2.ChatComponentText;
import net.minecraft.server.v1_16_R2.DamageSource;
import net.minecraft.server.v1_16_R2.EntityCreature;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.GenericAttributes;
import net.minecraft.server.v1_16_R2.SoundEffect;
import net.minecraft.server.v1_16_R2.World;

import java.util.Objects;

public class CustomEntity extends CustomEntityMoving {

    private static final String DEFAULT_NAME = "CustomEntity";
    private static final int DEFAULT_LEVEL = 1;

    private CustomNameBuilder nameBuilder;
    private CustomColor nameColor;
    private SoundPalette<SoundEffect> soundPalette;
    private String customNameTag;
    private int customLevel;

    protected CustomEntity(final EntityTypes<? extends EntityCreature> entityTypes, final World world) {
        super(entityTypes, world);
        this.nameBuilder = new NameBuilder();
        this.nameColor = CustomColor.LIGHT_RED;
        this.customNameTag = DEFAULT_NAME;
        this.customLevel = DEFAULT_LEVEL;
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

    public void setNameColor(CustomColor nameColor) {
        this.nameColor = nameColor;
    }

    public void setNameBuilder(CustomNameBuilder customNameBuilder) {
        this.nameBuilder = customNameBuilder;
    }

    public void setCustomNameTag(String customNameTag) {
        this.customNameTag = customNameTag;
    }

    public void setCustomLevel(int customLevel) {
        this.customLevel = customLevel;
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        this.updateName();
        super.setCustomNameVisible(visible);
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float damage) {
        boolean success = super.damageEntity(damageSource, damage);
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

    public void setSoundPalette(SoundPalette<SoundEffect> soundPalette) {
        this.soundPalette = soundPalette;
    }

    public static CustomEntityBuilder<CustomEntity> builder() {
        return new EntityBuilder<>();
    }
}
