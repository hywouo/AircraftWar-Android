package com.example.aircraftwar_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar_android.R;

public class DifficultiesSelectActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnEasy;
    private Button btnNormal;
    private Button btnHard;
    private Button btnLunatic;
    private Intent intent;
    private String difficultNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulties_select);
        btnEasy = findViewById(R.id.btnEasy);
        btnNormal = findViewById(R.id.btnNormal);
        btnHard = findViewById(R.id.btnHard);
        btnLunatic = findViewById(R.id.btnLunatic);


        btnEasy.setOnClickListener(this);
        btnNormal.setOnClickListener(this);
        btnHard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEasy:
                startEasy();
                break;
            case R.id.btnNormal:
                startNormal();
                break;
            case R.id.btnHard:
                startHard();
                break;
        }

    }

    private void startEasy(){
        difficultNum = "0";
        intent = new Intent();
        intent.putExtra("difficultNum",difficultNum);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    private void startNormal(){
        difficultNum = "1";
        intent = new Intent();
        intent.putExtra("difficultNum",difficultNum);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    private void startHard(){
        difficultNum = "2";
        intent = new Intent();
        intent.putExtra("difficultNum",difficultNum);
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
    }
}
