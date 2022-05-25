package com.example.aircraftwar_android.factory.propFactory;

import com.example.aircraftwar_android.props.AbstractProp;

public abstract class PropFactory {
    public abstract AbstractProp createProp(int x,int y ,int speedX,int speedY);
}
