package com.appodeal.support.test.mvp;

import android.util.Log;

import com.appodeal.ads.NativeAd;
import com.appodeal.support.test.repo.AppodealNativeData;
import com.appodeal.support.test.repo.Timer;

import java.util.List;

import io.reactivex.Observable;

public class Model {
    private static final int COUNTDOWN_INIT_VALUE = 30;
    private static Model ourInstance;
    private AppodealNativeData data;
    private Timer mTimer;
    private long mCurrentTime;
    private long mCountdown;


    private Model() {
        this.mTimer = new Timer();
        data = new AppodealNativeData();
        this.mCountdown = COUNTDOWN_INIT_VALUE;
    }

    public static Model getInstance() {
        if (ourInstance == null) {
            ourInstance = new Model();
        }
        return ourInstance;
    }

    public Observable<Long> getmTimer() {
        return mTimer.getNewintervalObservable();
    }

    public void setCurrentTimeTick() {
        mCurrentTime++;
        Log.d("CURRENTTIME", "setCurrentTimeTick: " + mCurrentTime);
    }

    public void setcountDownTick() {
        mCountdown--;
    }


    public long getmCurrentTime() {
        return mCurrentTime;
    }

    public long getmCountdown() {
        return mCountdown;
    }


    public void restartTimer() {
        mCountdown = COUNTDOWN_INIT_VALUE;
    }

    public List<NativeAd> getData() {
        return data.getData();
    }
}
