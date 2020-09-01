package de.dicecraft.dicemobmanager.entity.datawatcher;

import de.dicecraft.dicemobmanager.utils.ReflectionHelper;
import net.minecraft.server.v1_16_R2.DataWatcher;
import net.minecraft.server.v1_16_R2.DataWatcherObject;
import net.minecraft.server.v1_16_R2.DataWatcherRegistry;
import net.minecraft.server.v1_16_R2.EntityVillager;
import net.minecraft.server.v1_16_R2.VillagerData;
import net.minecraft.server.v1_16_R2.VillagerProfession;
import net.minecraft.server.v1_16_R2.VillagerType;
import org.bukkit.entity.Villager;

public class VillagerDataWatcher implements CustomDataWatcher {

    private static final DataWatcherObject<VillagerData> data = DataWatcher.a(EntityVillager.class, DataWatcherRegistry.q);

    private final Villager.Type type;
    private final Villager.Profession profession;

    public VillagerDataWatcher(final Villager.Type type, final Villager.Profession profession) {
        this.type = type;
        this.profession = profession;
    }

    @Override
    public void install(final DataWatcher dataWatcher) {
        final VillagerType type = ReflectionHelper.getEnumField(VillagerType.class, this.type.name());
        final VillagerProfession profession = ReflectionHelper.getEnumField(VillagerProfession.class, this.profession.name());
        dataWatcher.register(data, new VillagerData(type, profession, 1));
    }
}
