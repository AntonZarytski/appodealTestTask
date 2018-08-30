package com.appodeal.support.test.repo;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.NativeAd;

import java.util.ArrayList;
import java.util.List;

public class AppodealNativeData {
    private List<NativeAd> list;

    public AppodealNativeData() {
        list = new ArrayList<>();
    }

    public List<NativeAd> getData() {
        return list = Appodeal.getNativeAds(8);
    }
}
