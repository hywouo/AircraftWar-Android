package com.example.aircraftwar_android.aircraft;

import com.example.aircraftwar_android.activity.MainActivity;
import com.example.aircraftwar_android.application.ImageManager;
import com.example.aircraftwar_android.bullet.BaseBullet;
import com.example.aircraftwar_android.props.AbstractProp;

import java.util.ArrayList;
import java.util.List;

public class HeroAircraft extends AbstractAircraft{
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 5;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹飞行方向(向上：-1，向下：1)
     */
    private int direction = -1;

    @Override
    public void setShootNum(int shootNum) {
        this.shootNum = shootNum;
    }

    @Override
    public void setPower(int power) {
        this.power = power;
    }

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

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */


    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    /**
     *   单例模式创建heroAircraft对象,double-checkd locking
     */
    private volatile static HeroAircraft heroAircraft;

    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft==null){
            synchronized (HeroAircraft.class){
                if(heroAircraft==null){
                    heroAircraft = new HeroAircraft(MainActivity.WINDOW_WIDTH / 2,
                            MainActivity.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 10000);
                }
            }
        }
        return heroAircraft;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    public List<BaseBullet> shoot() {
        return new ArrayList<>();
    }


    @Override
    public AbstractProp propsProduce() {
        AbstractProp prop = new AbstractProp(locationX,locationY,0,5) {
            @Override
            public void work(HeroAircraft heroAircraft) {
            }
        };
        return prop;
    }

    @Override
    public void update() {

    }
}
