package com.example.aircraftwar_android.aircraft;

import com.example.aircraftwar_android.bullet.BaseBullet;
import com.example.aircraftwar_android.factory.propFactory.BloodPropFactory;
import com.example.aircraftwar_android.factory.propFactory.BombPropFactory;
import com.example.aircraftwar_android.factory.propFactory.BulletPropFactory;
import com.example.aircraftwar_android.props.AbstractProp;

import java.util.ArrayList;
import java.util.List;

public class BossAircraft extends AbstractAircraft{

    @Override
    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 7;

    /**
     * 子弹伤害
     */
    private int power = 10;

    @Override
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    @Override
    public int getShootNum() {
        return this.shootNum;
    }


    @Override
    public int getPower() {
        return this.power;
    }

    @Override
    public int getDirection() {
        return this.direction;
    }

    public BossAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        return new ArrayList<>();
    }

    @Override
    public AbstractProp propsProduce() {
        //产生随机数1-4之间的随机数
        int switchNum =  (int) (Math.random() * (4 - 1 + 1) + 1);
        switch (switchNum){
            case 1 :
                return new BloodPropFactory().createProp(this.getLocationX(),this.getLocationY(),0,5);
            case 2 :
                return new BombPropFactory().createProp(this.getLocationX(),this.getLocationY(),0,5);
            case 3:
                return new BulletPropFactory().createProp(this.getLocationX(),this.getLocationY(),0,5);
            default:
                return null;
        }
    }

    @Override
    public void update() {
        this.decreaseHp(500);
    }
}
