package de.dicecraft.dicemobmanager.entity.enchatment;

import de.dicecraft.dicemobmanager.entity.EntityManager;
import de.dicecraft.dicemobmanager.entity.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.event.ItemDropEvent;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.Map;

public class LootingHandler implements EnchantmentHandler {

    private final EntityManager manager;

    public LootingHandler(final EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(LivingEntity attacked, ProtoEntity<?> protoEntity, Player attacker) {
        int lootBonus = 0;
        if (attacker != null) {
            Map<Enchantment, Integer> enchantments = attacker.getInventory()
                    .getItemInMainHand().getEnchantments();
            lootBonus = enchantments.getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
        }
        if (manager.canItemsDrop()) {
            for (DeathDrop deathDrop : protoEntity.getDeathDrops()) {
                if (deathDrop.shouldDrop(lootBonus)) {
                    Location location = attacked.getLocation();
                    Item item = location.getWorld().dropItemNaturally(location, deathDrop.getItemStack().clone());
                    callItemDrop(protoEntity, new ItemDropEvent(protoEntity, deathDrop, item), attacked);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Mob> void callItemDrop(ProtoEntity<T> protoEntity, ItemDropEvent event, Entity entity) {
        protoEntity.onItemDrop(event, (T) entity);
    }
}
