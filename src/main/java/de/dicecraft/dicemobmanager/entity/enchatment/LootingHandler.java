package de.dicecraft.dicemobmanager.entity.enchatment;

import de.dicecraft.dicemobmanager.configuration.ConfigFlag;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LootingHandler implements EnchantmentHandler {

    private final EntityManager manager;

    public LootingHandler(final EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(final LivingEntity attacked, final ProtoEntity<?> protoEntity, final Player attacker) {
        int lootBonus = 0;
        if (attacker != null) {
            final Map<Enchantment, Integer> enchantments = attacker.getInventory()
                    .getItemInMainHand().getEnchantments();
            lootBonus = enchantments.getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
        }
        if (manager.canItemsDrop()) {
            List<DeathDrop> drops = new ArrayList<>();
            for (final DeathDrop deathDrop : protoEntity.getDeathDrops()) {
                if (deathDrop.shouldDrop(lootBonus)) {
                    drops.add(deathDrop);
                }
            }
            final Optional<Configuration> optional = manager.getEntityConfig(attacked);
            if (optional.isPresent()) {
                final Configuration configuration = optional.get();
                drops = configuration.getItemDropHandler().handleDrops(drops);
                for (final DeathDrop deathDrop : drops) {
                    final Location location = attacked.getLocation();
                    final Item item = location.getWorld().dropItemNaturally(location, deathDrop.getItemStack().clone());
                    item.setCanMobPickup(false);
                    if (attacker != null && attacker.getType() == EntityType.PLAYER
                            && configuration.shouldCancel(ConfigFlag.DROP_PICKUP_BY_EVERYONE)) {
                        item.setOwner(attacker.getUniqueId());
                        manager.watchItem(item);
                    }
                    callItemDrop(protoEntity, new ItemDropEvent(protoEntity, deathDrop, item), attacked);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Mob> void callItemDrop(final ProtoEntity<T> protoEntity, final ItemDropEvent event,
                                              final Entity entity) {
        protoEntity.onItemDrop(event, (T) entity);
    }
}
