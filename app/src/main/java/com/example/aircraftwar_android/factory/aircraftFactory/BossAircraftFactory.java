package com.example.aircraftwar_android.factory.aircraftFactory;

import com.example.aircraftwar_android.activity.MainActivity;
import com.example.aircraftwar_android.aircraft.AbstractAircraft;
import com.example.aircraftwar_android.aircraft.BossAircraft;
import com.example.aircraftwar_android.application.ImageManager;

public class BossAircraftFactory extends EnemyAircraftFactory{
    public  static int  initialHp = 1000;

    @Override
    public AbstractAircraft createEnemyAircraft() {
        return new BossAircraft((int) (Math.random() * (MainActivity.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())) * 1,
                (int) (Math.random() * MainActivity.WINDOW_HEIGHT * 0.2) * 1,
                20,
                0,
                initialHp);
    }
}
