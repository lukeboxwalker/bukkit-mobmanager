package de.dicecraft.dicemobmanager.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum Source {
    FIRE("inFire"),
    LIGHTNING("lightningBolt"),
    BURN("onFire"),
    LAVA("lava"),
    HOT_FLOOR("hotFloor"),
    STUCK("inWall"),
    CRAMMING("cramming"),
    DROWN("drown"),
    STARVE("starve"),
    CACTUS("cactus"),
    FALL("fall"),
    FLY_INTO_WALL("flyIntoWall"),
    OUT_OF_WORLD("outOfWorld"),
    GENERIC("generic"),
    MAGIC("magic"),
    WITHER("wither"),
    ANVIL("anvil"),
    FALLING_BLOCK("fallingBlock"),
    DRAGON_BREATH("dragonBreath"),
    DRYOUT("dryout"),
    SWEET_BERRY_BUSH("sweetBerryBush"),
    EXPLOSION("explosion"),
    ENTITY("sting", "mob", "player", "arrow", "trident", "fireworks", "onFire", "fireball",
            "witherSkull", "thrown", "indirectMagic", "thorns", "explosion.player");

    private final Set<String> names;

    Source(final String... names) {
        this.names = new HashSet<>(Arrays.asList(names));
    }

    public Set<String> getNames() {
        return names;
    }
}
