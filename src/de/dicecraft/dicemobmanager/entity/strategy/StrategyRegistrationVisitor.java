package de.dicecraft.dicemobmanager.entity.strategy;

import javax.annotation.Nonnull;

public interface StrategyRegistrationVisitor {

    void registerDeathStrategy(@Nonnull DeathStrategy strategy);

    void registerSpawnStrategy(@Nonnull SpawnStrategy strategy);

    void registerDamageStrategy(@Nonnull DamageStrategy strategy);

    void registerItemDropStrategy(@Nonnull ItemDropStrategy strategy);

    void registerTickStrategy(@Nonnull TickStrategy strategy);
}
