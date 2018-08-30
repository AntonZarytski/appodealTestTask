package com.appodeal.support.test.mvp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.Native;
import com.appodeal.ads.NativeAd;
import com.appodeal.ads.NativeCallbacks;
import com.appodeal.support.test.R;
import com.appodeal.support.test.adapter.ListViewAdapter;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends MvpAppCompatActivity implements ViewInterface {
    private final static String TAG = "MainActivity";
    private final static String APP_KEY = "3d80f7a0aa125eba4c5dc908d977e84789ae38f764e571c9";
    @InjectPresenter
    Presenter presenter;
    @BindView(R.id.current_time_after_start_value)
    TextView cuurentTimeAfterStart;
    @BindView(R.id.countdown_value)
    TextView countDown;
    @BindView(R.id.list_view)
    ListView nativeListView;
    @BindView(R.id.native_is_loading)
    ProgressBar progressBar;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAppodeal();
        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int i, boolean b) {
                Log.d(TAG, "onBannerLoaded");
            }

            @Override
            public void onBannerFailedToLoad() {

            }

            @Override
            public void onBannerShown() {
                Log.d(TAG, "onBannerShown");
                presenter.showBanner();
            }

            @Override
            public void onBannerClicked() {

            }
        });
        Appodeal.setNativeCallbacks(new NativeCallbacks() {
            @Override
            public void onNativeLoaded() {
                presenter.nativeWasLoaded();
            }

            @Override
            public void onNativeFailedToLoad() {
                Log.d("Appodeal", "onNativeFailedToLoad");
            }

            @Override
            public void onNativeShown(NativeAd nativeAd) {
                Log.d("Appodeal", "onNativeShown");
            }

            @Override
            public void onNativeClicked(NativeAd nativeAd) {
                Log.d("Appodeal", "onNativeClicked");
            }
        });
    }

    private void initAppodeal() {
        Appodeal.disableLocationPermissionCheck();
        Appodeal.initialize(this, APP_KEY, Appodeal.BANNER | Appodeal.INTERSTITIAL | Appodeal.NATIVE);
        Appodeal.setNativeAdType(Native.NativeAdType.NoVideo);
        Appodeal.cache(this, Appodeal.NATIVE, 9);
    }

    @OnClick(R.id.test_btn)
    public void mainBtnClick() {
        presenter.mainBtnClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        Appodeal.onResume(this, Appodeal.BANNER);
        presenter.onResume();
    }

    @Override
    public void setCountdown(String countdown) {
        countDown.setText(countdown);
    }

    @Override
    public void showInterstitial() {
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }

    @Override
    public void showBanner() {
        Appodeal.show(this, Appodeal.BANNER_TOP);
    }

    @Override
    public void hideBanner() {
        Appodeal.hide(this, Appodeal.BANNER_TOP);
    }

    @Override
    public void setTimeAfterStart(String currentTime) {
        cuurentTimeAfterStart.setText(String.valueOf(currentTime));
    }

    @Override
    public void showNativeListView() {
        nativeListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setConnectionError() {
        Toast.makeText(this, R.string.check_connection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setDataToAdapter(List<NativeAd> data) {
        if (adapter == null) {
            adapter = new ListViewAdapter(getApplicationContext(), data);
            nativeListView.setAdapter(adapter);
        } else adapter.updateData(data);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Appodeal.destroy(Appodeal.BANNER | Appodeal.INTERSTITIAL | Appodeal.NATIVE);
    }

}