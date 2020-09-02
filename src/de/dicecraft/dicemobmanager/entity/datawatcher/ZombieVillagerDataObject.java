package de.dicecraft.dicemobmanager.entity.datawatcher;

import de.dicecraft.dicemobmanager.entity.CustomEntity;
import de.dicecraft.dicemobmanager.utils.ReflectionHelper;
import net.minecraft.server.v1_16_R2.DataWatcher;
import net.minecraft.server.v1_16_R2.DataWatcherObject;
import net.minecraft.server.v1_16_R2.DataWatcherRegistry;
import net.minecraft.server.v1_16_R2.VillagerData;
import net.minecraft.server.v1_16_R2.VillagerProfession;
import net.minecraft.server.v1_16_R2.VillagerType;
import org.bukkit.entity.Villager;

public class ZombieVillagerDataObject implements CustomDataObject {

    private static final DataWatcherObject<Boolean> BABY_DATA = DataWatcher.a(CustomEntity.class, DataWatcherRegistry.i);
    private static final DataWatcherObject<Integer> INTEGER_DATA = DataWatcher.a(CustomEntity.class, DataWatcherRegistry.b);
    private static final DataWatcherObject<Boolean> DROWN_CONVERTING_DATA = DataWatcher.a(CustomEntity.class, DataWatcherRegistry.i);
    private static final DataWatcherObject<Boolean> CONVERTING_DATA = DataWatcher.a(CustomEntity.class, DataWatcherRegistry.i);
    private static final DataWatcherObject<VillagerData> VILLAGER_DATA = DataWatcher.a(CustomEntity.class, DataWatcherRegistry.q);

    private final Villager.Type type;
    private final Villager.Profession profession;

    public ZombieVillagerDataObject(final Villager.Type type, final Villager.Profession profession) {
        this.type = type;
        this.profession = profession;
    }

    @Override
    public void install(final DataWatcher dataWatcher) {
        final VillagerType type = ReflectionHelper.getEnumField(VillagerType.class, this.type.name());
        final VillagerProfession profession = ReflectionHelper.getEnumField(VillagerProfession.class, this.profession.name());

        dataWatcher.register(BABY_DATA, false);
        dataWatcher.register(INTEGER_DATA, 0);
        dataWatcher.register(DROWN_CONVERTING_DATA, false);
        dataWatcher.register(CONVERTING_DATA, false);
        dataWatcher.register(VILLAGER_DATA, new VillagerData(type, profession, 1));
    }
}
