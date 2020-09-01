package de.dicecraft.dicemobmanager.entity;

public class SoundPalette<T> {

    public static final String AMBIENT = "AMBIENT";
    public static final String DEATH = "DEATH";
    public static final String HURT = "HURT";
    public static final String STEP = "STEP";

    private T ambient;
    private T hurt;
    private T death;
    private T step;

    public void setAmbient(T ambient) {
        this.ambient = ambient;
    }

    public void setHurt(T hurt) {
        this.hurt = hurt;
    }

    public void setDeath(T death) {
        this.death = death;
    }

    public void setStep(T step) {
        this.step = step;
    }

    public T getAmbient() {
        return ambient;
    }

    public T getHurt() {
        return hurt;
    }

    public T getDeath() {
        return death;
    }

    public T getStep() {
        return step;
    }
}
