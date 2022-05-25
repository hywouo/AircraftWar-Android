package com.example.aircraftwar_android.shootStrategy;

import com.example.aircraftwar_android.aircraft.AbstractAircraft;
import com.example.aircraftwar_android.aircraft.BossAircraft;
import com.example.aircraftwar_android.aircraft.EliteAircraft;
import com.example.aircraftwar_android.bullet.BaseBullet;
import com.example.aircraftwar_android.bullet.EnemyBullet;
import com.example.aircraftwar_android.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ScatteringShoot implements ShootStrategy{
    private AbstractAircraft abstractAircraft;

    public ScatteringShoot(AbstractAircraft abstractAircraft) {
        this.abstractAircraft = abstractAircraft;
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = abstractAircraft.getLocationX();
        int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
        int speedX = 0;
        int speedY = abstractAircraft.getSpeedY() + abstractAircraft.getDirection()*24;
        BaseBullet abstractBullet;
        int flag ;
        for(int i=0; i<abstractAircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if(i<abstractAircraft.getShootNum()/2){
                flag=-1;
            }else if(i>abstractAircraft.getShootNum()/2){
                flag=1;
            }else{
                flag = 0;
            }

            if(abstractAircraft instanceof EliteAircraft || abstractAircraft instanceof BossAircraft) {
                abstractBullet =
                        new EnemyBullet(x - 20 + (i * 3 - abstractAircraft.getShootNum() + 1) * 10, y,
                                flag * 8 *( Math.abs(i - abstractAircraft.getShootNum() / 2)), speedY,
                                abstractAircraft.getPower());
            }else{
                abstractBullet =
                        new HeroBullet(x - 20 + (i * 3 - abstractAircraft.getShootNum() + 1) * 10, y,
                                flag * 3*( Math.abs(i - abstractAircraft.getShootNum() / 2)), speedY,
                                abstractAircraft.getPower());
            }
            res.add(abstractBullet);
        }
        return res;
    }
}
