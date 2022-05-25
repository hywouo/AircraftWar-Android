package com.example.aircraftwar_android.game;


import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.aircraftwar_android.activity.MainActivity;
import com.example.aircraftwar_android.R;
import com.example.aircraftwar_android.aircraft.AbstractAircraft;
import com.example.aircraftwar_android.aircraft.BossAircraft;
import com.example.aircraftwar_android.aircraft.EliteAircraft;
import com.example.aircraftwar_android.aircraft.HeroAircraft;
import com.example.aircraftwar_android.application.HeroController;
import com.example.aircraftwar_android.application.ImageManager;
import com.example.aircraftwar_android.basic.AbstractFlyingObject;
import com.example.aircraftwar_android.bomb.BombEffect;
import com.example.aircraftwar_android.bullet.BaseBullet;
import com.example.aircraftwar_android.musicService.MusicService;
import com.example.aircraftwar_android.observerPatten.Publisher;
import com.example.aircraftwar_android.props.AbstractProp;
import com.example.aircraftwar_android.props.BombProp;
import com.example.aircraftwar_android.shootStrategy.ScatteringShoot;
import com.example.aircraftwar_android.shootStrategy.SingleShoot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;


public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    /**
     * 测试变量
     */
    protected static final String TAG = "GameView";
    protected static int i = 0;
    protected static int j = 0;

    /**
     * 音乐服务,播放背景音乐
     */
    public static boolean musicOn = false;
    public MediaPlayer mp = new MediaPlayer();
    public AssetFileDescriptor bgmFile = getResources().openRawResourceFd(R.raw.bgm);
    MusicService musicService = new MusicService();

    protected int screenWidth,screenHeight;
    protected final SurfaceHolder surfaceHolder;


    protected Canvas canvas;
    protected final Paint paint;
    protected Bitmap backgroundImage = ImageManager.BACKGROUND_IMAGE_EASY;

    protected int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    protected ScheduledExecutorService executorService;
    protected android.os.Handler handler;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 11;
    protected int time = 0;

    /**
     * 敌机、子弹、道具集合
     */
    protected List<AbstractAircraft> enemyAircrafts;
    protected  List<BaseBullet> heroBullets;
    protected  List<BaseBullet> enemyBullets;
    protected  List<AbstractProp> props;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    protected int cycleDuration = 100;
    protected int cycleTime = 10;

    /**
     * 指示精英敌机相对于普通敌机产生频率的一个计数器
     * 使得 countMob/countElite = multiple
     */
    protected int countMob = 0;
    protected int countElite = 1;
    protected int multiple = 12;
    protected int enemyMaxNumber = 5;

    /**
     * 产生boss机的分数阈值
     */
    protected int ScoreThreshold ;


    /**
     * 游戏得分
     */
    public static int score = 0;

    /**
     * 击败各种敌机所获得的分数
     */
    public static int EliteAircraftScore = 30;
    protected static int MobAircraftScore = 10;
    protected static int BossAircraftScore = 1000;



    /**
     * 单例模式创建英雄机
     */
    protected final HeroAircraft heroAircraft ;

    /**
     * 爆炸效果
     */
    public BombEffect bombEffect;

    /**
     *观察者模式，加入发布者
     */
    protected final Publisher publisher;

    public GameView(Context context){
        super(context);
        heroAircraft = HeroAircraft.getHeroAircraft();
        heroAircraft.setShootStrategy(new SingleShoot(heroAircraft));

        enemyAircrafts = new ArrayList<>();
        heroBullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
        props = new ArrayList<>();
        publisher = new Publisher();

        /**
         * 线程池
         */
        ThreadFactory gameThread = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("gameThread");
                return t;

            }
        };
        executorService = new ScheduledThreadPoolExecutor(1,
                gameThread);

        handler = new Handler();

        paint = new Paint();
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        this.setFocusable(true);

        //启动触摸监听英雄机
        this.setOnTouchListener(new HeroController(this,heroAircraft));

        try {
            mp.setDataSource(bgmFile.getFileDescriptor(),bgmFile.getStartOffset(),bgmFile.getLength());
            bgmFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        screenHeight = height;
        screenWidth = width;
        Log.i("out", "surfaceChanged: "+screenWidth+screenHeight);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
    }


    @Override
    public void run() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            //播放背景音乐

            if (timeCountAndNewCycleJudge()) {
                // 新敌机产生
                createEnemy();
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            //道具移动
            propsMoveAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            handler.postDelayed(this,timeInterval);

            // 游戏结束检查
            saveData();




        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        //executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

        stopTask(this);
        handler.postDelayed(task, timeInterval);

    }

    private void stopTask(Runnable task){
        if(task != null){
            handler.removeCallbacks(task);
            task = null;
        }
    }


    private final void saveData() {
        if (heroAircraft.getHp() <= 0) {

            if(musicOn) {
                MainActivity.myBinder2.playGameOver();
            }
            stopTask(this);

//
            System.out.println("Game Over!");
        }
    }

    /**
     * 不同难度的游戏类，重写改方法
     */

    protected void createEnemy() {

    }


    private final boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private final void shootAction() {
        // TODO 敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.setShootStrategy(new SingleShoot(enemyAircraft));
            if (enemyAircraft instanceof BossAircraft) {
                enemyAircraft.setShootStrategy(new ScatteringShoot(enemyAircraft));
            }
            List<BaseBullet> enemyBulletList = enemyAircraft.getShootStrategy().shoot();
            enemyBullets.addAll(enemyBulletList);

            //加入观察
            for(BaseBullet bullet : enemyBulletList){
                publisher.addObserver(bullet);
            }

        }

        // 英雄射击
        List<BaseBullet> heroBulletList = heroAircraft.getShootStrategy().shoot();
        heroBullets.addAll(heroBulletList);


        //加入观察
        for(BaseBullet bullet : heroBulletList){
            publisher.addObserver(bullet);
        }

    }

    private final void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private final void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private final void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private final void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet enemyBullet : enemyBullets) {
            if (enemyBullet.notValid()) {
                continue;
            }
            if (enemyBullet.crash(heroAircraft)) {
                heroAircraft.decreaseHp(enemyBullet.getPower());
                enemyBullet.vanish();
                continue;
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值

                    //播放音乐
                    if(musicOn) {
                        MainActivity.myBinder1.playBullet();
                    }



                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        //产生爆炸的图片
                        Runnable k = ()->{
                            bombEffect = new BombEffect(enemyAircraft.getLocationX(),enemyAircraft.getLocationY());
                            try {
                                bombEffect.work(enemyAircraft.getLocationX(),enemyAircraft.getLocationY());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        };
                        new Thread(k).start();

                        // TODO 获得分数，产生道具补给
                        if (enemyAircraft instanceof EliteAircraft) {
                            AbstractProp prop = (enemyAircraft).propsProduce();
                            if (prop != null) {
                                props.add(prop);
                            }
                            score += EliteAircraftScore;
                        } else if (enemyAircraft instanceof BossAircraft) {

                            for (int i = 0; i < 6; i++) {

                                AbstractProp prop = (enemyAircraft).propsProduce();
                                if (prop != null) {
                                    prop.setLocation(enemyAircraft.getLocationX() + i * 10 * Math.random(), enemyAircraft.getLocationY() + i * 10 * Math.random());
                                    props.add(prop);
                                    System.out.println("i = "+i);
                                }
                            }
                            score += BossAircraftScore;
                        } else {
                            score += MobAircraftScore;
                        }


                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {

                //播放音效
                if (musicOn) {
                    MainActivity.myBinder2.playGetSupply();
                }

                if(prop instanceof BombProp){
                    publisher.notifyAllObserver();
                }


                prop.work(heroAircraft);
                prop.vanish();
                continue;
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private final void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractProp::notValid);
    }

    private void repaint(){

        synchronized (surfaceHolder) {
            myDraw();
        }

    }

    private void myDraw(){
        canvas = surfaceHolder.lockCanvas();
        if(canvas == null) return;

        //循环绘制背景图片
        canvas.drawBitmap(backgroundImage,0,backGroundTop-backgroundImage.getHeight(),paint);
        canvas.drawBitmap(backgroundImage,0,backGroundTop,paint);
        backGroundTop+=1;
        if(backGroundTop == backgroundImage.getHeight()) backGroundTop = 0;

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(enemyBullets);
        paintImageWithPositionRevised(heroBullets);


        paintImageWithPositionRevised(enemyAircrafts);
        paintImageWithPositionRevised(props);

        canvas.drawBitmap(ImageManager.HERO_IMAGE,heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2,paint);

        //绘制爆炸图像
        if(bombEffect!=null){
            paintBombEffect(bombEffect);
        }

        //绘制得分和生命值
        paintScoreAndLife();

        surfaceHolder.unlockCanvasAndPost(canvas);

    }
    private final void paintImageWithPositionRevised(List<? extends AbstractFlyingObject> objects){
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            Bitmap image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image,object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2,paint);
        }
    }

    private final void paintScoreAndLife(){
        int x = 10;
        int y = 25;

        Paint paint1 = new Paint();
        paint1.setTextSize(30);
        paint1.setColor(Color.RED);
        Typeface myFont = Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD);
        paint1.setTypeface(myFont);
        canvas.drawText("SCORE:" + this.score, x, y,paint1);
        y = y + 50;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y,paint1);
    }

    private final void paintBombEffect(BombEffect bombEffect ){
        if(bombEffect.flag1){ canvas.drawBitmap(ImageManager.BOMB1,bombEffect.x, bombEffect.y, paint);}
        if(bombEffect.flag2){canvas.drawBitmap(ImageManager.BOMB2,bombEffect.x, bombEffect.y,paint);}
        if(bombEffect.flag3){ canvas.drawBitmap(ImageManager.BOMB3,bombEffect.x, bombEffect.y, paint);}
        if(bombEffect.flag4){canvas.drawBitmap(ImageManager.BOMB4,bombEffect.x, bombEffect.y, paint);}
    }
}
