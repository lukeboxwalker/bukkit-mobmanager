package de.dicecraft.dicemobmanager.command;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.configuration.ConfigFlag;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.factory.SkullFactory;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.CustomDeathDrop;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.factory.EntitySpawnFactory;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnEntityCommand implements Command {

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                DeathDrop deathDrop = new CustomDeathDrop(new ItemStack(Material.DIAMOND), 0.5, DeathDrop.Rarity.LEGENDARY);

                ProtoEntity entity = DiceMobManager.builder()
                        .setType(EntityType.CREEPER)
                        .setDeathDrops(Collections.singleton(deathDrop))
                        .setAttribute(Attribute.GENERIC_MAX_HEALTH, 1)
                        .setName("Test")
                        .build();

                Configuration configuration = DiceMobManager.configBuilder()
                        .setBooleanFlag(ConfigFlag.SLIME_SPLIT, false)
                        .setBooleanFlag(ConfigFlag.PROJECTILE_BLOCK_DAMAGE, false)
                        .setBooleanFlag(ConfigFlag.CREEPER_EXPLOSION_DAMAGE, false)
                        .build();

                EntitySpawnFactory factory = DiceMobManager.createSpawnFactory(configuration);
                factory.spawnEntity(entity, player.getLocation());
                return true;
            } catch (EntityCreationException e) {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public List<String> tabComplete(final CommandSender sender, final String[] args) {
        return new ArrayList<>();
    }
}
