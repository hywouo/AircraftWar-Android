package com.example.aircraftwar_android.shootStrategy;

import com.example.aircraftwar_android.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    /**
     * 射击策略方法
     * 返回子弹集合
     *
     */
    List<BaseBullet> shoot();
}
