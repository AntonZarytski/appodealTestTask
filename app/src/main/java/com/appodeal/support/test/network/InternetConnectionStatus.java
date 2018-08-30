package com.appodeal.support.test.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.appodeal.support.test.App;

public class InternetConnectionStatus {

    static boolean isAirplane() {
        return Settings.System.getInt(App.getInstance().getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1;
    }

    static Status getStatus() {
        Status currentStatus = Status.OFFLINE;
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                currentStatus = Status.WIFI;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
                currentStatus = Status.ETHERNET;
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                currentStatus = Status.MOBILE;
            }
        } else {
            currentStatus = Status.OFFLINE;
        }
        return currentStatus;
    }

    public static boolean isOnline() {
        Status currentStatus = getStatus();
        return currentStatus.equals(Status.WIFI) || currentStatus.equals(Status.ETHERNET) || currentStatus.equals(Status.MOBILE);
    }

    static boolean isWifi() {
        return getStatus().equals(Status.WIFI);
    }

    static boolean isEthernet() {
        return getStatus().equals(Status.ETHERNET);
    }

    static boolean isMobile() {
        return getStatus().equals(Status.MOBILE);
    }

    static boolean isOffline() {
        return getStatus().equals(Status.OFFLINE);
    }

    enum Status {
        WIFI,
        MOBILE,
        ETHERNET,
        OFFLINE
    }
}
