package com.example.aircraftwar_android.application;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.example.aircraftwar_android.aircraft.HeroAircraft;
import com.example.aircraftwar_android.game.GameView;

/**
 * 英雄机控制类
 * 监听触摸位置，控制英雄机的移动
 */

public class HeroController implements View.OnTouchListener{

    private GameView game;
    private HeroAircraft heroAircraft;

    public HeroController(GameView game,HeroAircraft heroAircraft){
        this.game = game;
        this.heroAircraft = heroAircraft;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            heroAircraft.setLocation(event.getX(),event.getY());
        }
        return true;
    }
}
