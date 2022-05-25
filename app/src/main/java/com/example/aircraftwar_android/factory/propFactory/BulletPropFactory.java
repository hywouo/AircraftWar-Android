package com.example.aircraftwar_android.factory.propFactory;

import com.example.aircraftwar_android.props.AbstractProp;
import com.example.aircraftwar_android.props.BulletProp;

public class BulletPropFactory extends PropFactory{
    @Override
    public AbstractProp createProp(int x, int y, int speedX, int speedY) {
        return new BulletProp(x,y,speedX,speedY);
    }
}
