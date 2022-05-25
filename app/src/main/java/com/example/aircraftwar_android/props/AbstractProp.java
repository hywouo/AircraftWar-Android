package com.example.aircraftwar_android.props;

import com.example.aircraftwar_android.activity.MainActivity;
import com.example.aircraftwar_android.aircraft.HeroAircraft;
import com.example.aircraftwar_android.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {
    public AbstractProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /**
     * 作用函数
     *
     * @param heroAircraft 作用对象为HeroAircraft英雄机
     *
     */
    public abstract void work(HeroAircraft heroAircraft);

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public void update() {

    }
}
