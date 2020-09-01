package de.dicecraft.dicemobmanager.adapter;

import org.bukkit.Bukkit;

import java.util.Arrays;

public final class NMSHandler {

    private static final CustomEntityFactory ENTITY_FACTORY;
    private static final String VERSION_1_16_2 = "1.16.2";

    static {
        final VersionAdapter version = Arrays.stream(VersionAdapter.values())
                .filter(versionAdapter -> Bukkit.getVersion().contains(versionAdapter.version))
                .findFirst().orElseThrow();
        ENTITY_FACTORY = version.adapter;
    }

    public static CustomEntityFactory getEntityFactory() {
        return ENTITY_FACTORY;
    }

    private enum VersionAdapter {
        V_16_2_R2(new NMS_EntityFactory_v1_16_R2(), VERSION_1_16_2);

        private final CustomEntityFactory adapter;
        private final String version;

        VersionAdapter(final CustomEntityFactory adapter, final String version) {
            this.adapter = adapter;
            this.version = version;
        }
    }
}
