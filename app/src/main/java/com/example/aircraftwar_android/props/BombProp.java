package com.example.aircraftwar_android.props;

import com.example.aircraftwar_android.aircraft.HeroAircraft;

public class BombProp extends AbstractProp{
    public BombProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void work(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
    }

    @Override
    public void forward() {
        super.forward();
    }

    @Override
    public void update() {
        super.update();
    }
}
