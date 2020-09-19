package de.dicecraft.dicemobmanager.command;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.configuration.ConfigFlag;
import de.dicecraft.dicemobmanager.configuration.Configuration;
import de.dicecraft.dicemobmanager.entity.factory.SkullFactory;
import de.dicecraft.dicemobmanager.entity.builder.ProtoEntity;
import de.dicecraft.dicemobmanager.entity.drops.CustomDeathDrop;
import de.dicecraft.dicemobmanager.entity.drops.DeathDrop;
import de.dicecraft.dicemobmanager.entity.event.SlimeEvent;
import de.dicecraft.dicemobmanager.entity.event.SpawnEvent;
import de.dicecraft.dicemobmanager.entity.factory.EntitySpawnFactory;
import de.dicecraft.dicemobmanager.entity.builder.EntityCreationException;
import de.dicecraft.dicemobmanager.entity.strategy.SlimeSplitStrategy;
import de.dicecraft.dicemobmanager.entity.strategy.SpawnStrategy;
import de.dicecraft.dicemobmanager.entity.strategy.StrategyRegistrationVisitor;
import net.minecraft.server.v1_16_R2.EntityWither;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpawnEntityCommand extends AbstractCommand {

    private final List<ItemStack> heads;

    public SpawnEntityCommand() {
        this.heads = new ArrayList<>();
        SkullFactory skullFactory = new SkullFactory();
        heads.add(skullFactory.createSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZlNjUwMmFjNGM4NDdiMWFjMzc4MTBkNjZkMjhjOTFhOGIxOGZkN2Y2MzgzMTI4MjI4NzU1YWE4YzhmNSJ9fX0="));
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            try {
                DeathDrop deathDrop = new CustomDeathDrop(new ItemStack(Material.DIAMOND), 0.5, DeathDrop.Rarity.LEGENDARY);

                ProtoEntity entity = DiceMobManager.builder()
                        .setType(EntityType.WITHER)
                        .setDeathDrops(Collections.singleton(deathDrop))
                        .ignoreGoal(VanillaGoal.NEAREST_ATTACKABLE_TARGET)
                        .setAttribute(Attribute.GENERIC_MAX_HEALTH, 1)
                        .setName("Test")
                        .build();

                Configuration configuration = DiceMobManager.configBuilder()
                        .setBooleanFlag(ConfigFlag.SLIME_SPLIT, false)
                        .setBooleanFlag(ConfigFlag.PROJECTILE_BLOCK_DAMAGE, false)
                        .build();

                EntitySpawnFactory factory = DiceMobManager.createSpawnFactory(configuration);
                factory.spawnEntity(entity, player.getLocation());

            } catch (EntityCreationException | IllegalArgumentException e) {
                e.printStackTrace();
            }

            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<String> tabComplete(final CommandSender sender, final String[] args) {
        return new ArrayList<>();
    }
}
