package com.appodeal.support.test.repo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class Timer {
    private Observable<Long> intervalObservable;

    public Timer() {
        intervalObservable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Long> getNewintervalObservable() {
        return intervalObservable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Long> getintervalObservable() {
        return intervalObservable;
    }
}
