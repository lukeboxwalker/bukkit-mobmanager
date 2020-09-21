package de.dicecraft.dicemobmanager.entity.strategy;

import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleStrategyManager implements StrategyManager, StrategyRegistrationVisitor {

    private final List<SpawnStrategy> onSpawnStrategies = new ArrayList<>();
    private final List<DamageStrategy> onDamageStrategies = new ArrayList<>();
    private final List<DeathStrategy> onDeathStrategies = new ArrayList<>();
    private final List<ItemDropStrategy> onItemDropStrategies = new ArrayList<>();
    private final List<TickStrategy> onTickStrategies = new ArrayList<>();

    private final HashMap<NamespacedKey, List<? extends Strategy>> keyMap = new HashMap<>();

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
    public void removeStrategy(@Nonnull NamespacedKey key) {
        if (keyMap.containsKey(key)) {
            keyMap.get(key).removeIf(strategy -> key.equals(strategy.getKey()));
            keyMap.remove(key);
        }
    }

    @Override
    public void addStrategy(@Nonnull Strategy strategy) {
        if (keyMap.containsKey(strategy.getKey())) {
            keyMap.get(strategy.getKey()).removeIf(entry -> entry.getKey().equals(strategy.getKey()));
        }
        strategy.accept(this);
    }

    @Override
    public void addStrategies(@Nonnull List<Strategy> strategies) {
        strategies.forEach(this::addStrategy);
    }

    @Nonnull
    @Override
    public List<DamageStrategy> getDamageStrategies() {
        return onDamageStrategies;
    }

    @Nonnull
    @Override
    public List<DeathStrategy> getDeathStrategies() {
        return onDeathStrategies;
    }

    @Nonnull
    @Override
    public List<ItemDropStrategy> getItemDropStrategies() {
        return onItemDropStrategies;
    }

    @Nonnull
    @Override
    public List<SpawnStrategy> getSpawnStrategies() {
        return onSpawnStrategies;
    }

    @Nonnull
    @Override
    public List<TickStrategy> getTickStrategies() {
        return onTickStrategies;
    }

    @Override
    public void registerDeathStrategy(@Nonnull DeathStrategy strategy) {
        onDeathStrategies.add(strategy);
        keyMap.put(strategy.getKey(), onDeathStrategies);
    }

    @Override
    public void registerSpawnStrategy(@Nonnull SpawnStrategy strategy) {
        onSpawnStrategies.add(strategy);
        keyMap.put(strategy.getKey(), onSpawnStrategies);
    }

    @Override
    public void registerDamageStrategy(@Nonnull DamageStrategy strategy) {
        onDamageStrategies.add(strategy);
        keyMap.put(strategy.getKey(), onDamageStrategies);
    }

    @Override
    public void registerItemDropStrategy(@Nonnull ItemDropStrategy strategy) {
        onItemDropStrategies.add(strategy);
        keyMap.put(strategy.getKey(), onItemDropStrategies);
    }

    @Override
    public void registerTickStrategy(@Nonnull TickStrategy strategy) {
        onTickStrategies.add(strategy);
        keyMap.put(strategy.getKey(), onTickStrategies);
    }
}
