package com.appodeal.support.test.mvp;

import com.appodeal.support.test.network.InternetConnectionStatus;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class Presenter extends MvpPresenter<ViewInterface> {
    public static final int END_OF_BANNER_SHOWING = 5;
    public static final int SHOW_INTERSTITIAL_INTERVAL = 30;
    public static final int FIRST_ROUND_INTERVAL = 30;
    public static final int CONNECTION_HINT_SHOWING_INTERWAL = 5;
    public long INTERWAL;
    private Model model;
    private Observer<Long> observer;
    private Disposable disposable;
    private boolean isNeedShowInterstitial;
    private boolean bannerWasShow;
    private boolean bannerStartShow;

    public Presenter() {
        super();
        this.model = Model.getInstance();
        this.isNeedShowInterstitial = true;

        observer = new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                if (InternetConnectionStatus.isOnline()) {
                    timeWasChanged();
                } else {
                    if (aLong % CONNECTION_HINT_SHOWING_INTERWAL == 0)
                        getViewState().setConnectionError();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }

    public void timeWasChanged() {
        model.setCurrentTimeTick();
        String currentTime = String.valueOf(model.getmCurrentTime());
        getViewState().setTimeAfterStart(currentTime);
        if (isNeedShowInterstitial) {
            model.setcountDownTick();
            String countdown = String.valueOf(model.getmCountdown());
            getViewState().setCountdown(countdown);
        }
        if (!bannerWasShow) {
            if (!bannerStartShow) {
                getViewState().showBanner();
                bannerStartShow = true;
            }
            if (model.getmCurrentTime() >= (INTERWAL + END_OF_BANNER_SHOWING)) {
                getViewState().hideBanner();
                bannerWasShow = true;
            }
        }
        if (model.getmCurrentTime() % SHOW_INTERSTITIAL_INTERVAL == 0) {
            if (isNeedShowInterstitial) {
                getViewState().showInterstitial();
                model.restartTimer();
            }
        }
    }

    public void mainBtnClick() {
        if (InternetConnectionStatus.isOnline()) {
            getViewState().showNativeListView();
            getViewState().showProgressBar();
        } else {
            getViewState().setConnectionError();
        }
        if (model.getmCurrentTime() <= FIRST_ROUND_INTERVAL) {
            isNeedShowInterstitial = false;
        }
    }

    public void onPause() {
        disposable.dispose();
    }

    public void onResume() {
        model.getmTimer().subscribe(observer);
    }

    public void nativeWasLoaded() {
        getViewState().setDataToAdapter(model.getData());
        getViewState().hideProgressBar();
    }

    public void showBanner() {
        INTERWAL = model.getmCurrentTime();
    }
}
