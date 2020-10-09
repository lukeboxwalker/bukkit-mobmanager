package de.dicecraft.dicemobmanager.entity.builder;

import org.bukkit.entity.*;

public final class CustomType<T extends Mob> {

    public static final CustomType<ElderGuardian> ELDER_GUARDIAN = new CustomType<>(EntityType.ELDER_GUARDIAN);
    public static final CustomType<WitherSkeleton> WITHER_SKELETON = new CustomType<>(EntityType.WITHER_SKELETON);
    public static final CustomType<Stray> STRAY = new CustomType<>(EntityType.STRAY);
    public static final CustomType<Husk> HUSK = new CustomType<>(EntityType.HUSK);
    public static final CustomType<ZombieVillager> ZOMBIE_VILLAGER = new CustomType<>(EntityType.ZOMBIE_VILLAGER);
    public static final CustomType<SkeletonHorse> SKELETON_HORSE = new CustomType<>(EntityType.SKELETON_HORSE);
    public static final CustomType<ZombieHorse> ZOMBIE_HORSE = new CustomType<>(EntityType.ZOMBIE_HORSE);
    public static final CustomType<Donkey> DONKEY = new CustomType<>(EntityType.DONKEY);
    public static final CustomType<Mule> MULE = new CustomType<>(EntityType.MULE);
    public static final CustomType<Evoker> EVOKER = new CustomType<>(EntityType.EVOKER);
    public static final CustomType<Vex> VEX = new CustomType<>(EntityType.VEX);
    public static final CustomType<Vindicator> VINDICATOR = new CustomType<>(EntityType.VINDICATOR);
    public static final CustomType<Illusioner> ILLUSIONER = new CustomType<>(EntityType.ILLUSIONER);
    public static final CustomType<Creeper> CREEPER = new CustomType<>(EntityType.CREEPER);
    public static final CustomType<Skeleton> SKELETON = new CustomType<>(EntityType.SKELETON);
    public static final CustomType<Spider> SPIDER = new CustomType<>(EntityType.SPIDER);
    public static final CustomType<Giant> GIANT = new CustomType<>(EntityType.GIANT);
    public static final CustomType<Zombie> ZOMBIE = new CustomType<>(EntityType.ZOMBIE);
    public static final CustomType<Slime> SLIME = new CustomType<>(EntityType.SLIME);
    public static final CustomType<Ghast> GHAST = new CustomType<>(EntityType.GHAST);
    public static final CustomType<PigZombie> ZOMBIFIED_PIGLIN = new CustomType<>(EntityType.ZOMBIFIED_PIGLIN);
    public static final CustomType<Enderman> ENDERMAN = new CustomType<>(EntityType.ENDERMAN);
    public static final CustomType<CaveSpider> CAVE_SPIDER = new CustomType<>(EntityType.CAVE_SPIDER);
    public static final CustomType<Silverfish> SILVERFISH = new CustomType<>(EntityType.SILVERFISH);
    public static final CustomType<Blaze> BLAZE = new CustomType<>(EntityType.BLAZE);
    public static final CustomType<MagmaCube> MAGMA_CUBE = new CustomType<>(EntityType.MAGMA_CUBE);
    public static final CustomType<EnderDragon> ENDER_DRAGON = new CustomType<>(EntityType.ENDER_DRAGON);
    public static final CustomType<Wither> WITHER = new CustomType<>(EntityType.WITHER);
    public static final CustomType<Bat> BAT = new CustomType<>(EntityType.BAT);
    public static final CustomType<Witch> WITCH = new CustomType<>(EntityType.WITCH);
    public static final CustomType<Endermite> ENDERMITE = new CustomType<>(EntityType.ENDERMITE);
    public static final CustomType<Guardian> GUARDIAN = new CustomType<>(EntityType.GUARDIAN);
    public static final CustomType<Shulker> SHULKER = new CustomType<>(EntityType.SHULKER);
    public static final CustomType<Pig> PIG = new CustomType<>(EntityType.PIG);
    public static final CustomType<Sheep> SHEEP = new CustomType<>(EntityType.SHEEP);
    public static final CustomType<Cow> COW = new CustomType<>(EntityType.COW);
    public static final CustomType<Chicken> CHICKEN = new CustomType<>(EntityType.CHICKEN);
    public static final CustomType<Squid> SQUID = new CustomType<>(EntityType.SQUID);
    public static final CustomType<Wolf> WOLF = new CustomType<>(EntityType.WOLF);
    public static final CustomType<MushroomCow> MUSHROOM_COW = new CustomType<>(EntityType.MUSHROOM_COW);
    public static final CustomType<Snowman> SNOWMAN = new CustomType<>(EntityType.SNOWMAN);
    public static final CustomType<Ocelot> OCELOT = new CustomType<>(EntityType.OCELOT);
    public static final CustomType<IronGolem> IRON_GOLEM = new CustomType<>(EntityType.IRON_GOLEM);
    public static final CustomType<Horse> HORSE = new CustomType<>(EntityType.HORSE);
    public static final CustomType<Rabbit> RABBIT = new CustomType<>(EntityType.RABBIT);
    public static final CustomType<PolarBear> POLAR_BEAR = new CustomType<>(EntityType.POLAR_BEAR);
    public static final CustomType<Llama> LLAMA = new CustomType<>(EntityType.LLAMA);
    public static final CustomType<Parrot> PARROT = new CustomType<>(EntityType.PARROT);
    public static final CustomType<Villager> VILLAGER = new CustomType<>(EntityType.VILLAGER);
    public static final CustomType<Turtle> TURTLE = new CustomType<>(EntityType.TURTLE);
    public static final CustomType<Phantom> PHANTOM = new CustomType<>(EntityType.PHANTOM);
    public static final CustomType<Cod> COD = new CustomType<>(EntityType.COD);
    public static final CustomType<Salmon> SALMON = new CustomType<>(EntityType.SALMON);
    public static final CustomType<PufferFish> PUFFERFISH = new CustomType<>(EntityType.PUFFERFISH);
    public static final CustomType<TropicalFish> TROPICAL_FISH = new CustomType<>(EntityType.TROPICAL_FISH);
    public static final CustomType<Drowned> DROWNED = new CustomType<>(EntityType.DROWNED);
    public static final CustomType<Dolphin> DOLPHIN = new CustomType<>(EntityType.DOLPHIN);
    public static final CustomType<Cat> CAT = new CustomType<>(EntityType.CAT);
    public static final CustomType<Panda> PANDA = new CustomType<>(EntityType.PANDA);
    public static final CustomType<Pillager> PILLAGER = new CustomType<>(EntityType.PILLAGER);
    public static final CustomType<Ravager> RAVAGER = new CustomType<>(EntityType.RAVAGER);
    public static final CustomType<TraderLlama> TRADER_LLAMA = new CustomType<>(EntityType.TRADER_LLAMA);
    public static final CustomType<WanderingTrader> WANDERING_TRADER = new CustomType<>(EntityType.WANDERING_TRADER);
    public static final CustomType<Fox> FOX = new CustomType<>(EntityType.FOX);
    public static final CustomType<Bee> BEE = new CustomType<>(EntityType.BEE);
    public static final CustomType<Hoglin> HOGLIN = new CustomType<>(EntityType.HOGLIN);
    public static final CustomType<Piglin> PIGLIN = new CustomType<>(EntityType.PIGLIN);
    public static final CustomType<Strider> STRIDER = new CustomType<>(EntityType.STRIDER);
    public static final CustomType<Zoglin> ZOGLIN = new CustomType<>(EntityType.ZOGLIN);
    public static final CustomType<PiglinBrute> PIGLIN_BRUTE = new CustomType<>(EntityType.PIGLIN_BRUTE);

    private final EntityType entityType;

    private CustomType(final EntityType entityType) {
        this.entityType = entityType;
    }

    public EntityType getEntityType() {
        return entityType;
    }
}
