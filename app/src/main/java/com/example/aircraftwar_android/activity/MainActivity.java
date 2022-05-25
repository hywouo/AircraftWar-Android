package com.example.aircraftwar_android.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar_android.R;
import com.example.aircraftwar_android.application.ImageManager;
import com.example.aircraftwar_android.game.EasyGame;
import com.example.aircraftwar_android.game.GameView;
import com.example.aircraftwar_android.game.HardGame;
import com.example.aircraftwar_android.game.NormalGame;
import com.example.aircraftwar_android.musicService.MusicService;

public class MainActivity extends AppCompatActivity {

    //屏幕的宽度、高度
    public static int WINDOW_HEIGHT;
    public static int WINDOW_WIDTH;
    private GameView game;

    /**
     * 音乐服务
     * myBinder1 用于播放子弹音效（子弹音效产生频率较高，分开两个myBinder效果比较好）
     * myBinder2 用于播放游戏结束音效、获取道具音效
     */
    public static MusicService.MyBinder myBinder1;
    public static MusicService.MyBinder myBinder2;
    public static MediaPlayer mediaPlayer;
    private Connect conn;
    private Intent intent;
    private Intent intent2;

    public static String difficultNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化屏幕参数
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        WINDOW_HEIGHT = display.heightPixels;
        WINDOW_WIDTH = display.widthPixels;

        //音效
        conn = new Connect();
        intent = new Intent(this,MusicService.class);
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
//
//        //背景音乐
//        intent2 = new Intent(this, MyIntentService.class);
//        String action = MyIntentService.ACTION_MUSIC;
//        // 设置action
//        intent2.setAction(action);
//        startService(intent2);
        mediaPlayer = MediaPlayer.create(this,R.raw.bgm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        mediaPlayer.setVolume(1.0f,1.0f);



        ImageManager.initial(getResources());
        String difficultNum = getIntent().getStringExtra("difficultNum");
        System.out.println("difficult = "+ difficultNum);
        if(difficultNum != null){
            switch (difficultNum){
                case "0":
                    game = new EasyGame(this);
                    break;
                case "1":
                    game = new NormalGame(this);
                    break;
                case "2":
                    game = new HardGame(this);
                    break;
                case "3":
                    System.out.println("LunaticGame");
                    break;
                default:
                    System.out.println("NoGame");
                    break;
            }
            setContentView(game);
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("music demo","stop");
        unbindService(conn);
    }

    class Connect implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("music demo", "Service Connnected");
            myBinder1 = (MusicService.MyBinder) service;
            myBinder2 = (MusicService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
}