package com.example.aircraftwar_android.props;

import com.example.aircraftwar_android.aircraft.HeroAircraft;
import com.example.aircraftwar_android.shootStrategy.ScatteringShoot;
import com.example.aircraftwar_android.shootStrategy.SingleShoot;

public class BulletProp extends AbstractProp{
    public BulletProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void work(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active!");
        Runnable r = () -> {

            try {
                heroAircraft.setShootStrategy(new ScatteringShoot(heroAircraft));
                Thread.sleep(10000);
                heroAircraft.setShootStrategy(new SingleShoot(heroAircraft));
            }catch (Exception e){
                e.printStackTrace();
            }
        };

        new Thread(r).start();
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
