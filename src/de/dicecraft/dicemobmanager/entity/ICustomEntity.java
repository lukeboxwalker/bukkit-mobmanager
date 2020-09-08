package de.dicecraft.dicemobmanager.entity;

import net.minecraft.server.v1_16_R2.SoundEffect;

public interface ICustomEntity {

    void setNameColor(CustomColor nameColor);

    void setNameBuilder(CustomNameBuilder customNameBuilder);

    void setCustomNameTag(String customNameTag);

    void setCustomLevel(int customLevel);

    void setDamageBehavior(DamageBehavior damageBehavior);

    void setBaseTickBehavior(BaseTickBehavior baseTickBehavior);

    void setMovementTickBehavior(MovementTickBehavior movementTickBehavior);

    void setMobTickBehavior(MobTickBehavior mobTickBehavior);

    void setSoundPalette(SoundPalette<SoundEffect> soundPalette);

    void setCustomNameVisible(boolean visible);
}
