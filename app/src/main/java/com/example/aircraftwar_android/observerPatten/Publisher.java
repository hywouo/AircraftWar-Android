package com.example.aircraftwar_android.observerPatten;

import com.example.aircraftwar_android.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<AbstractFlyingObject> observerList = new ArrayList<>();

    public void addObserver(AbstractFlyingObject observer){
        observerList.add(observer);
    }

    public void removeObserver(AbstractFlyingObject observer){
        observerList.remove(observer);
    }

    public void notifyAllObserver(){
        for(AbstractFlyingObject observer : observerList){
            observer.update();
        }
    }
}
