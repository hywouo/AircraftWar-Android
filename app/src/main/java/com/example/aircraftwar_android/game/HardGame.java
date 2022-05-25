package com.example.aircraftwar_android.game;

import android.content.Context;

import com.example.aircraftwar_android.aircraft.AbstractAircraft;
import com.example.aircraftwar_android.aircraft.BossAircraft;
import com.example.aircraftwar_android.aircraft.EliteAircraft;
import com.example.aircraftwar_android.application.ImageManager;
import com.example.aircraftwar_android.factory.aircraftFactory.BossAircraftFactory;
import com.example.aircraftwar_android.factory.aircraftFactory.EliteAircraftFactory;
import com.example.aircraftwar_android.factory.aircraftFactory.MobEnemyFactory;
import com.example.aircraftwar_android.shootStrategy.SingleShoot;

public class HardGame extends GameView{


    public HardGame(Context context){
        super(context);
        super.backgroundImage = ImageManager.BACKGROUND_IMAGE_DIFFICULT;
        super.ScoreThreshold = 500;
    }

    @Override
    protected void createEnemy() {
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
        /**
         * 防止分数发生跳跃，设置一个区间
         */
        boolean bossAppear = (score >= ScoreThreshold && score % ScoreThreshold == 0 ||
                score > ScoreThreshold && score % ScoreThreshold < Math.max(EliteAircraftScore, MobAircraftScore));

        if (bossAppear) {

            boolean bossFlag = false;

            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (!(enemyAircraft instanceof BossAircraft)) {
                    bossFlag = true;
                } else {
                    bossFlag = false;
                    break;
                }
            }
            if (bossFlag) {
                BossAircraft boss = (BossAircraft) new BossAircraftFactory().createEnemyAircraft();
                enemyAircrafts.add(boss);
            }


        }
    }
}
