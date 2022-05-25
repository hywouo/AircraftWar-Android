package com.example.aircraftwar_android.bomb;

public class BombEffect {
    public int x;
    public int y;
    public boolean flag1 = false;
    public boolean flag2 = false;
    public boolean flag3 = false;
    public boolean flag4 = false;

    public BombEffect(int x,int y){
        this.x = x;
        this.y = y;
    }
    public  void work(int x, int y) throws InterruptedException {
        new Bomb1(x, y);
        flag1 = true;
        Thread.sleep(80);
        flag1 = false;
        new Bomb2(x,y);
        flag2 = true;
        Thread.sleep(80);
        flag2 = false;
        new Bomb3(x,y);
        flag3 = true;
        Thread.sleep(80);
        flag3 = false;
        new Bomb4(x,y);
        flag4 = true;
        Thread.sleep(80);
        flag4 = false;
    }
}
