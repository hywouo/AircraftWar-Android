package com.example.aircraftwar_android.aircraft;

import com.example.aircraftwar_android.basic.AbstractFlyingObject;
import com.example.aircraftwar_android.bullet.BaseBullet;
import com.example.aircraftwar_android.props.AbstractProp;
import com.example.aircraftwar_android.shootStrategy.ShootStrategy;

import java.util.List;

public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    /**
     * 射击策略
     */
    protected ShootStrategy shootStrategy;

    /**
     * 子弹飞行方向
     * 向上：-1，向下：1
     */
    private int direction;

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum ;

    /**
     * 子弹伤害
     */
    private int power;



    public int getShootNum() {
        return shootNum;
    }



    public int getPower() {
        return power;
    }


    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public int getDirection() {
        return direction;
    }

    public ShootStrategy getShootStrategy() {
        return shootStrategy;
    }

    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();

    /**
     * 产生道具的一个抽象方法
     * @return
     */
    public abstract AbstractProp propsProduce();
}
