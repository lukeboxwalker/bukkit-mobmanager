package de.dicecraft.dicemobmanager.entity.strategy;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.List;

public interface StrategyManager<T extends Mob> {

    void removeAllStrategies();

    void removeStrategy(@Nonnull NamespacedKey key);

    void addStrategy(@Nonnull Strategy<? super T> strategy);

    void addStrategies(@Nonnull List<Strategy<? super T>> strategies);

    @Nonnull
    List<DamageStrategy<? super T>> getDamageStrategies();

    @Nonnull
    List<DeathStrategy<? super T>> getDeathStrategies();

    @Nonnull
    List<ItemDropStrategy<? super T>> getItemDropStrategies();

    @Nonnull
    List<SpawnStrategy<? super T>> getSpawnStrategies();

    @Nonnull
    List<TickStrategy<? super T>> getTickStrategies();
}
