package com.example.aircraftwar_android.factory.aircraftFactory;

import com.example.aircraftwar_android.activity.MainActivity;
import com.example.aircraftwar_android.aircraft.AbstractAircraft;
import com.example.aircraftwar_android.aircraft.MobEnemy;
import com.example.aircraftwar_android.application.ImageManager;

public class MobEnemyFactory extends EnemyAircraftFactory{
    public static int initialHp = 100;

    @Override
    public AbstractAircraft createEnemyAircraft() {
        return new MobEnemy((int) (Math.random() * (MainActivity.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * MainActivity.WINDOW_HEIGHT * 0.2) * 1,
                0,
                30,
                initialHp);
    }
}
