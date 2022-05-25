package com.example.aircraftwar_android.musicService;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.aircraftwar_android.R;

import java.util.HashMap;

public class MusicService extends Service {
    private static  final String TAG = "MusicService";
    private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();
    private SoundPool mSoundPool;
    private MediaPlayer player1;
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "==== MusicService onCreate ===");
        mSoundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
        soundID.put(1, mSoundPool.load(this, R.raw.bullet_hit, 1));
        soundID.put(2, mSoundPool.load(this, R.raw.game_over, 1));
        soundID.put(3,mSoundPool.load(this, R.raw.get_supply,1));
        soundID.put(4,mSoundPool.load(this,R.raw.bullet,1));
    }

    @Override
    public IBinder onBind(Intent intent){
        playMusic();
        return new MyBinder();
    }

    public class MyBinder extends Binder {

        public void playBullet(){
            mSoundPool.play(soundID.get(1), 1, 1, 0,0,1);
        }

        public void playGameOver(){

            mSoundPool.play(soundID.get(2), 1, 1, 0, 0, 1);
        }

        public void playGetSupply(){
            mSoundPool.play(soundID.get(3),1,1,0,0,1);
        }

        public void playBulletShoot(){
            mSoundPool.play(soundID.get(4), 1, 1, 0, 0, 1);
        }

    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    //播放音乐
    public  void playMusic(){
        if(player1 == null){
           player1 = MediaPlayer.create(this, R.raw.bgm);
           player1.setLooping(true);
        }
           player1.start();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
    }
    /**
     * 停止播放
     */
    public void stopMusic() {
        if (player1 != null) {
            player1.stop();
            player1.reset();//重置
            player1.release();//释放
            player1 = null;
        }
    }
}