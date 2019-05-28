package com.mp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static int TYPE_NOT_CONNECTED= -1;

    public static boolean isConnected(Context context) {
        int connectivityType = getConnectivityType(context);
        if (connectivityType == TYPE_NOT_CONNECTED)
            return false;
        else
            return true;
    }

    public static int getConnectivityType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (null != networkInfo && networkInfo.isConnected()) {
            return networkInfo.getType();
        }
        return TYPE_NOT_CONNECTED;
    }

}

