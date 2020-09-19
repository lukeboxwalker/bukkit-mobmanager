package de.dicecraft.dicemobmanager.entity.strategy;

import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;
import java.util.List;

public interface StrategyManager {

    void removeAllStrategies();

    void removeStrategy(@Nonnull NamespacedKey key);

    void addStrategy(@Nonnull Strategy strategy);

    void addStrategies(@Nonnull List<Strategy> strategies);

    @Nonnull
    List<DamageStrategy> getDamageStrategies();

    @Nonnull
    List<DeathStrategy> getDeathStrategies();

    @Nonnull
    List<ItemDropStrategy> getItemDropStrategies();

    @Nonnull
    List<SpawnStrategy> getSpawnStrategies();

    @Nonnull
    List<TickStrategy> getTickStrategies();
}
