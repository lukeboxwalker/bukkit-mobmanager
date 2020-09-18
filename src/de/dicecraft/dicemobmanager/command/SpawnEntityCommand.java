package de.dicecraft.dicemobmanager.command;

import com.destroystokyo.paper.entity.ai.VanillaGoal;
import de.dicecraft.dicemobmanager.DiceMobManager;
import de.dicecraft.dicemobmanager.entity.SkullFactory;
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
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
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
                        .setType(EntityType.SLIME)
                        .setDeathDrops(Collections.singleton(deathDrop))
                        .ignoreGoal(VanillaGoal.NEAREST_ATTACKABLE_TARGET)
                        .setAttribute(Attribute.GENERIC_MAX_HEALTH, 120)
                        .addStrategy(new SlimeSplitStrategy() {

                            private final NamespacedKey key = DiceMobManager.createNameSpacedKey("cancel_slime_split");

                            @Override
                            public void play(SlimeEvent slimeEvent) {
                                slimeEvent.getBukkitEvent().setCancelled(true);
                            }

                            @Nonnull
                            @Override
                            public NamespacedKey getKey() {
                                return key;
                            }

                            @Override
                            public void accept(StrategyRegistrationVisitor registrationVisitor) {
                                registrationVisitor.registerSlimeSplitStrategy(this);
                            }
                        })
                        .addStrategy(new SpawnStrategy() {

                            private final NamespacedKey key = DiceMobManager.createNameSpacedKey("change_slime_size");

                            @Override
                            public void play(SpawnEvent spawnEvent) {
                                double health = spawnEvent.getEntity().getHealth();
                                // setSize on slime resets the health to size^2
                                ((Slime)  spawnEvent.getEntity()).setSize(5);
                                AttributeInstance instance =  spawnEvent.getEntity().getAttribute(Attribute.GENERIC_MAX_HEALTH);
                                if (instance != null) {
                                    instance.setBaseValue(spawnEvent.getProtoEntity().getAttributeMap().get(Attribute.GENERIC_MAX_HEALTH));
                                    spawnEvent.getEntity().setHealth(health);
                                }
                            }

                            @Nonnull
                            @Override
                            public NamespacedKey getKey() {
                                return key;
                            }

                            @Override
                            public void accept(StrategyRegistrationVisitor registrationVisitor) {
                                registrationVisitor.registerSpawnStrategy(this);
                            }
                        })
                        .setName("Test")
                        .build();

                EntitySpawnFactory factory = DiceMobManager.createSpawnFactory();
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
