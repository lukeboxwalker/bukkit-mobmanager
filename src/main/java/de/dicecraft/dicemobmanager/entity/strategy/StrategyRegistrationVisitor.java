package de.dicecraft.dicemobmanager.entity.strategy;

import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;

public interface StrategyRegistrationVisitor {

    void registerDeathStrategy(@Nonnull DeathStrategy<? extends Mob> strategy);

    void registerSpawnStrategy(@Nonnull SpawnStrategy<? extends Mob> strategy);

    void registerDamageStrategy(@Nonnull DamageStrategy<? extends Mob> strategy);

    void registerItemDropStrategy(@Nonnull ItemDropStrategy<? extends Mob> strategy);

    void registerTickStrategy(@Nonnull TickStrategy<? extends Mob> strategy);
}
