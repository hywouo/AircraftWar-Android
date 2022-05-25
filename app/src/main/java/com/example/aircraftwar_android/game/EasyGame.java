package com.example.aircraftwar_android.game;

import android.content.Context;

import com.example.aircraftwar_android.aircraft.AbstractAircraft;
import com.example.aircraftwar_android.aircraft.EliteAircraft;
import com.example.aircraftwar_android.application.ImageManager;
import com.example.aircraftwar_android.factory.aircraftFactory.EliteAircraftFactory;
import com.example.aircraftwar_android.factory.aircraftFactory.MobEnemyFactory;
import com.example.aircraftwar_android.shootStrategy.SingleShoot;

public class EasyGame extends GameView{

    public EasyGame(Context context) {
        super(context);
        super.backgroundImage = ImageManager.BACKGROUND_IMAGE_EASY;


    }

    @Override
    protected void createEnemy(){
        // 新敌机产生

        if (enemyAircrafts.size() < enemyMaxNumber) {
            AbstractAircraft mobAircraft = new MobEnemyFactory().createEnemyAircraft();
            enemyAircrafts.add(mobAircraft);
            publisher.addObserver(mobAircraft);
            countMob++;
            if ((countMob / countElite) == multiple) {
                EliteAircraft eliteAircraft = (EliteAircraft) new EliteAircraftFactory().createEnemyAircraft();
                eliteAircraft.setShootStrategy(new SingleShoot(eliteAircraft));
                enemyAircrafts.add(eliteAircraft);
                publisher.addObserver(eliteAircraft);
                countElite++;
            }
        }
    }
}
