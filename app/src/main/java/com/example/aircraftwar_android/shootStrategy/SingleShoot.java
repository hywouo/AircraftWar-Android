package com.example.aircraftwar_android.shootStrategy;

import com.example.aircraftwar_android.aircraft.AbstractAircraft;
import com.example.aircraftwar_android.aircraft.BossAircraft;
import com.example.aircraftwar_android.aircraft.EliteAircraft;
import com.example.aircraftwar_android.bullet.BaseBullet;
import com.example.aircraftwar_android.bullet.EnemyBullet;
import com.example.aircraftwar_android.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class SingleShoot implements ShootStrategy{

    private AbstractAircraft abstractAircraft;

    public SingleShoot(AbstractAircraft abstractAircraft) {this.abstractAircraft = abstractAircraft;}

    @Override
    public List<BaseBullet> shoot() {

            List<BaseBullet> res = new LinkedList<>();
            int x = abstractAircraft.getLocationX();
            int y = abstractAircraft.getLocationY() + abstractAircraft.getDirection()*2;
            int speedX = 0;
            int speedY = abstractAircraft.getSpeedY() + abstractAircraft.getDirection()*24;
            BaseBullet abstractBullet;
            for(int i=0; i<abstractAircraft.getShootNum(); i++){
                // 子弹发射位置相对飞机位置向前偏移
                // 多个子弹横向分散
                if(abstractAircraft instanceof EliteAircraft || abstractAircraft instanceof BossAircraft) {
                    abstractBullet = new EnemyBullet(x + (i * 2 - abstractAircraft.getShootNum() + 1) * 30, y, speedX, speedY, abstractAircraft.getPower());
                }else{
                    abstractBullet = new HeroBullet(x + (i * 2 - abstractAircraft.getShootNum() + 1) * 30, y, speedX, speedY, abstractAircraft.getPower());

                }
                res.add(abstractBullet);
            }
            return res;
        }
    }

