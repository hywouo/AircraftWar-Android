package com.example.aircraftwar_android.aircraft;

import com.example.aircraftwar_android.activity.MainActivity;
import com.example.aircraftwar_android.bullet.BaseBullet;
import com.example.aircraftwar_android.game.GameView;
import com.example.aircraftwar_android.props.AbstractProp;

import java.util.ArrayList;
import java.util.List;

public class MobEnemy extends AbstractAircraft{
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.WINDOW_HEIGHT ) {
            vanish();
        }
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
        if(this.isValid) {
            this.vanish();
            GameView.score += GameView.EliteAircraftScore;
        }

    }
}
