package de.dicecraft.dicemobmanager.entity.strategy;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleStrategyManager<T extends Mob> implements StrategyManager<T>, StrategyRegistrationVisitor {

    private static final String UNCHECKED = "unchecked";

    private final List<SpawnStrategy<? super T>> onSpawnStrategies = new ArrayList<>();
    private final List<DamageStrategy<? super T>> onDamageStrategies = new ArrayList<>();
    private final List<DeathStrategy<? super T>> onDeathStrategies = new ArrayList<>();
    private final List<ItemDropStrategy<? super T>> onItemDropStrategies = new ArrayList<>();
    private final List<TickStrategy<? super T>> onTickStrategies = new ArrayList<>();

    private final Map<NamespacedKey, List<? extends Strategy<?>>> keyMap = new HashMap<>();

    @Override
    public void removeAllStrategies() {
        onSpawnStrategies.clear();
        onDamageStrategies.clear();
        onDeathStrategies.clear();
        onItemDropStrategies.clear();
        onTickStrategies.clear();
        keyMap.clear();
    }

    @Override
    public void removeStrategy(final @Nonnull NamespacedKey key) {
        if (keyMap.containsKey(key)) {
            keyMap.get(key).removeIf(strategy -> key.equals(strategy.getKey()));
            keyMap.remove(key);
        }
    }

    @Override
    public void addStrategy(final @Nonnull Strategy<? super T> strategy) {
        if (keyMap.containsKey(strategy.getKey())) {
            keyMap.get(strategy.getKey()).removeIf(entry -> entry.getKey().equals(strategy.getKey()));
        }
        strategy.accept(this);
    }

    @Override
    public void addStrategies(final @Nonnull List<Strategy<? super T>> strategies) {
        strategies.forEach(this::addStrategy);
    }

    @Nonnull
    @Override
    public List<DamageStrategy<? super T>> getDamageStrategies() {
        return onDamageStrategies;
    }

    @Nonnull
    @Override
    public List<DeathStrategy<? super T>> getDeathStrategies() {
        return onDeathStrategies;
    }

    @Nonnull
    @Override
    public List<ItemDropStrategy<? super T>> getItemDropStrategies() {
        return onItemDropStrategies;
    }

    @Nonnull
    @Override
    public List<SpawnStrategy<? super T>> getSpawnStrategies() {
        return onSpawnStrategies;
    }

    @Nonnull
    @Override
    public List<TickStrategy<? super T>> getTickStrategies() {
        return onTickStrategies;
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    public void registerDeathStrategy(final @Nonnull DeathStrategy<? extends Mob> strategy) {
        onDeathStrategies.add((DeathStrategy<? super T>) strategy);
        keyMap.put(strategy.getKey(), onDeathStrategies);
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    public void registerSpawnStrategy(final @Nonnull SpawnStrategy<? extends Mob> strategy) {
        onSpawnStrategies.add((SpawnStrategy<? super T>) strategy);
        keyMap.put(strategy.getKey(), onSpawnStrategies);
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    public void registerDamageStrategy(final @Nonnull DamageStrategy<? extends Mob> strategy) {
        onDamageStrategies.add((DamageStrategy<? super T>) strategy);
        keyMap.put(strategy.getKey(), onDamageStrategies);
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    public void registerItemDropStrategy(final @Nonnull ItemDropStrategy<? extends Mob> strategy) {
        onItemDropStrategies.add((ItemDropStrategy<? super T>) strategy);
        keyMap.put(strategy.getKey(), onItemDropStrategies);
    }

    @Override
    @SuppressWarnings(UNCHECKED)
    public void registerTickStrategy(final @Nonnull TickStrategy<? extends Mob> strategy) {
        onTickStrategies.add((TickStrategy<? super T>) strategy);
        keyMap.put(strategy.getKey(), onTickStrategies);
    }
}
