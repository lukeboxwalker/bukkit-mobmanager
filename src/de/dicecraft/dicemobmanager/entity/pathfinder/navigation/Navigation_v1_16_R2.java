package de.dicecraft.dicemobmanager.entity.pathfinder.navigation;

import de.dicecraft.dicemobmanager.entity.CustomEntityMoving;
import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.Navigation;
import net.minecraft.server.v1_16_R2.PathEntity;
import net.minecraft.server.v1_16_R2.World;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftEntity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.Location;

public class Navigation_v1_16_R2 extends Navigation implements CustomNavigation {

    public Navigation_v1_16_R2(final CustomEntityMoving customEntity, final World world) {
        super(customEntity, world);
    }

    @Override
    public void resetPathEntity() {
        super.o();
    }

    @Override
    public boolean checkPath(final LivingEntity entity, final double d) {
        assert entity != null;
        return super.a(((CraftEntity) entity).getHandle(), d);
    }

    @Override
    public CustomPathEntity createPathEntity(final LivingEntity entity, final int i) {
        assert entity != null;
        return new CustomPathEntity_v1_16_R2(super.a(((CraftEntity) entity).getHandle(), i));
    }

    @Override
    public CustomPathEntity createPathEntity(final Location location, final int i) {
        assert location != null;
        return new CustomPathEntity_v1_16_R2(super.a(locationToBlockPosition(location), i));
    }

    @Override
    public void walkPathEntity(final CustomPathEntity customPathEntity, double speed) {
        assert customPathEntity != null;
        super.a(((CustomPathEntity_v1_16_R2) customPathEntity).pathEntity, speed);
    }

    @Override
    public boolean hasNoPathEntity() {
        return super.n();
    }

    private BlockPosition locationToBlockPosition(final Location location) {
        assert location != null;
        return new BlockPosition(location.getX(), location.getY(), location.getZ());
    }

    private static class CustomPathEntity_v1_16_R2 implements CustomPathEntity {

        private final PathEntity pathEntity;

        private CustomPathEntity_v1_16_R2(final PathEntity pathEntity) {
            this.pathEntity = pathEntity;
        }

        @Override
        public boolean hasPath() {
            return pathEntity != null;
        }
    }
}
