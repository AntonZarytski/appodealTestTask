package com.appodeal.support.test.mvp;

import com.appodeal.ads.NativeAd;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
interface ViewInterface extends MvpView {
    void setCountdown(String countdown);

    void setTimeAfterStart(String currentTime);

    @StateStrategyType(SkipStrategy.class)
    void showInterstitial();

    @StateStrategyType(SkipStrategy.class)
    void setConnectionError();

    @StateStrategyType(SkipStrategy.class)
    void hideProgressBar();

    @StateStrategyType(SkipStrategy.class)
    void showProgressBar();

    void showBanner();

    void hideBanner();

    void showNativeListView();

    void setDataToAdapter(List<NativeAd> data);

}
