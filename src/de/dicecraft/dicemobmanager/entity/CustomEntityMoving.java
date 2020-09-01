package de.dicecraft.dicemobmanager.entity;

import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoal;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.CustomPathfinderGoalTarget;
import de.dicecraft.dicemobmanager.entity.pathfinder.navigation.CustomNavigation;
import de.dicecraft.dicemobmanager.entity.pathfinder.goal.PathFinderGoalFactory_v1_16_R2;
import de.dicecraft.dicemobmanager.entity.pathfinder.navigation.Navigation_v1_16_R2;

import net.minecraft.server.v1_16_R2.BlockPosition;
import net.minecraft.server.v1_16_R2.EntityCreature;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.EnumHand;
import net.minecraft.server.v1_16_R2.NavigationAbstract;
import net.minecraft.server.v1_16_R2.World;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.MainHand;

import javax.annotation.Nullable;

public class CustomEntityMoving extends EntityCreature {

    private final PathFinderGoalFactory_v1_16_R2 pathFinderGoalFactory;

    protected CustomEntityMoving(EntityTypes<? extends EntityCreature> entityTypes, World world) {
        super(entityTypes, world);
        this.pathFinderGoalFactory = new PathFinderGoalFactory_v1_16_R2(this);
    }

    @Override
    public NavigationAbstract getNavigation() {
        if (this.isPassenger() && this.getVehicle() instanceof CustomEntity) {
            CustomEntity customEntity = (CustomEntity) this.getVehicle();
            return customEntity.getNavigation();
        } else {
            return this.navigation;
        }
    }

    @Nullable
    public LivingEntity getTargetGoal() {
        if (super.getGoalTarget() == null) {
            return null;
        } else {
            return super.getGoalTarget().getBukkitLivingEntity();
        }
    }

    public boolean checkLocation(final Location location) {
        assert location != null;
        return this.a(new BlockPosition(location.getX(), location.getY(), location.getZ()));
    }

    public void swingHand(MainHand mainHand) {
        if (MainHand.RIGHT.equals(mainHand)) {
            super.swingHand(EnumHand.MAIN_HAND);
        } else {
            super.swingHand(EnumHand.OFF_HAND);
        }
    }

    @Override
    protected NavigationAbstract b(final World world) {
        return new Navigation_v1_16_R2(this, world);
    }

    public CustomNavigation getCustomNavigation() {
        return (CustomNavigation) getNavigation();
    }

    public void addPathfinderGoal(final int priority, final CustomPathfinderGoal pathfinderGoal) {
        this.goalSelector.a(priority, pathFinderGoalFactory.createPathFinderGoal(pathfinderGoal));
    }

    public void addPathfinderTarget(final int priority, final CustomPathfinderGoalTarget pathfinderGoalTarget) {
        this.targetSelector.a(priority, pathFinderGoalFactory.createPathFinderGoalTarget(pathfinderGoalTarget));
    }

}
