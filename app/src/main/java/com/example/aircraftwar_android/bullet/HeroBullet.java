package com.example.aircraftwar_android.bullet;

public class HeroBullet extends BaseBullet{
    public HeroBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public void update() {
        this.vanish();
    }
}
