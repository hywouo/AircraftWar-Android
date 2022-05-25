package com.example.aircraftwar_android.props;

import com.example.aircraftwar_android.aircraft.HeroAircraft;

public class BloodProp extends AbstractProp{

    /**
     * 定义加血道具的加血值
     */
    private int hpAdding = 100;

    public BloodProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void work(HeroAircraft heroAircraft) {
        heroAircraft.decreaseHp(-this.hpAdding);
    }

    @Override
    public void update() {
        super.update();
    }
}
