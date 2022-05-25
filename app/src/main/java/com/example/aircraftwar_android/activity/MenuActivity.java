package com.example.aircraftwar_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar_android.R;
import com.example.aircraftwar_android.game.GameView;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStart ;
    private Button btnEnd;
    private Intent intent;
    private Switch musicSwitch;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        /**
         * 按钮的绑定
         */
        btnStart = (Button) findViewById(R.id.btnStart);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        musicSwitch = (Switch) findViewById(R.id.musicSwitch);

        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        musicSwitch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnStart:
                start();
                break;
            case R.id.btnEnd:
                System.exit(0);
                break;
            case R.id.musicSwitch:
                GameView.musicOn = !GameView.musicOn;
                break;
        }
    }

    public void start(){
            intent = new Intent();
            intent.setClass(MenuActivity.this, DifficultiesSelectActivity.class);
            startActivity(intent);
    }
}
