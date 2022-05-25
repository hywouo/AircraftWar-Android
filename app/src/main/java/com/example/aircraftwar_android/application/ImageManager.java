package com.example.aircraftwar_android.application;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.aircraftwar_android.R;
import com.example.aircraftwar_android.aircraft.BossAircraft;
import com.example.aircraftwar_android.aircraft.EliteAircraft;
import com.example.aircraftwar_android.aircraft.HeroAircraft;
import com.example.aircraftwar_android.aircraft.MobEnemy;
import com.example.aircraftwar_android.bomb.Bomb1;
import com.example.aircraftwar_android.bomb.Bomb2;
import com.example.aircraftwar_android.bomb.Bomb3;
import com.example.aircraftwar_android.bomb.Bomb4;
import com.example.aircraftwar_android.bullet.EnemyBullet;
import com.example.aircraftwar_android.bullet.HeroBullet;
import com.example.aircraftwar_android.props.BloodProp;
import com.example.aircraftwar_android.props.BombProp;
import com.example.aircraftwar_android.props.BulletProp;

import java.util.HashMap;
import java.util.Map;

public class ImageManager {
    /**
     * 类名-图片 映射，存储各基类的图片 <br>
     * 可使用 CLASSNAME_IMAGE_MAP.get( obj.getClass().getName() ) 获得 obj 所属基类对应的图片
     */
    private static final Map<String, Bitmap> CLASSNAME_IMAGE_MAP = new HashMap<>();

    public static Bitmap BACKGROUND_IMAGE_EASY;
    public static Bitmap BACKGROUND_IMAGE_COMMON;
    public static Bitmap BACKGROUND_IMAGE_DIFFICULT;

    public static Bitmap HERO_IMAGE;
    public static Bitmap HERO_BULLET_IMAGE;
    public static Bitmap ENEMY_BULLET_IMAGE;
    public static Bitmap MOB_ENEMY_IMAGE;
    public static Bitmap ELITE_IMAGE;
    public static Bitmap PROP_BLOOD;
    public static Bitmap PROP_BOMB;
    public static Bitmap PROP_BULLET;
    public static Bitmap BOSS_IMAGE;

    public static Bitmap BOMB1;
    public static Bitmap BOMB2;
    public static Bitmap BOMB3;
    public static Bitmap BOMB4;


    public static void initial(Resources resources) {

        BitmapFactory.Options options = new BitmapFactory.Options();

            try {

                BACKGROUND_IMAGE_EASY = BitmapFactory.decodeResource(resources, R.drawable.bg,options);
                BACKGROUND_IMAGE_COMMON= BitmapFactory.decodeResource(resources, R.drawable.bg2,options);
                BACKGROUND_IMAGE_DIFFICULT = BitmapFactory.decodeResource(resources,R.drawable.bg5,options);

                HERO_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.hero,options);
                MOB_ENEMY_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.mob,options);
                ELITE_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.elite,options);
                BOSS_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.boss,options);

                PROP_BLOOD = BitmapFactory.decodeResource(resources,R.drawable.prop_blood,options);
                PROP_BOMB = BitmapFactory.decodeResource(resources,R.drawable.prop_bomb,options);
                PROP_BULLET = BitmapFactory.decodeResource(resources,R.drawable.prop_bullet,options);

                HERO_BULLET_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.bullet_hero,options);
                ENEMY_BULLET_IMAGE = BitmapFactory.decodeResource(resources,R.drawable.bullet_enemy,options);

                BOMB1 = BitmapFactory.decodeResource(resources,R.drawable.bom1,options);
                BOMB2 = BitmapFactory.decodeResource(resources,R.drawable.bom2,options);
                BOMB3 = BitmapFactory.decodeResource(resources,R.drawable.bom3,options);
                BOMB4 = BitmapFactory.decodeResource(resources,R.drawable.bom4,options);

                CLASSNAME_IMAGE_MAP.put(HeroAircraft.class.getName(),HERO_IMAGE);
                CLASSNAME_IMAGE_MAP.put(MobEnemy.class.getName(),MOB_ENEMY_IMAGE);
                CLASSNAME_IMAGE_MAP.put(EliteAircraft.class.getName(),ELITE_IMAGE);
                CLASSNAME_IMAGE_MAP.put(BossAircraft.class.getName(),BOSS_IMAGE);

                CLASSNAME_IMAGE_MAP.put(BloodProp.class.getName(),PROP_BLOOD);
                CLASSNAME_IMAGE_MAP.put(BombProp.class.getName(),PROP_BOMB);
                CLASSNAME_IMAGE_MAP.put(BulletProp.class.getName(),PROP_BULLET);

                CLASSNAME_IMAGE_MAP.put(HeroBullet.class.getName(),HERO_BULLET_IMAGE);
                CLASSNAME_IMAGE_MAP.put(EnemyBullet.class.getName(),ENEMY_BULLET_IMAGE);

                CLASSNAME_IMAGE_MAP.put(Bomb1.class.getName(),BOMB1);
                CLASSNAME_IMAGE_MAP.put(Bomb2.class.getName(),BOMB2);
                CLASSNAME_IMAGE_MAP.put(Bomb3.class.getName(),BOMB3);
                CLASSNAME_IMAGE_MAP.put(Bomb4.class.getName(),BOMB4);


            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }

    }

    public static Bitmap get(String className){
        return CLASSNAME_IMAGE_MAP.get(className);
    }

    public static Bitmap get(Object obj){
        if(obj == null){
            return null;
        }
        else{
            return get(obj.getClass().getName());
        }
    }



}
