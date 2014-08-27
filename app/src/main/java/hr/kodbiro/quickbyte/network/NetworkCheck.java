/*
 * Copyright © 2014 Marko Filipović sifilis@kset.org
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2, as published by Sam Hocevar
 */

package hr.kodbiro.quickbyte.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by marko on 25.8.2014..
 * Class for checking network connection state
 */
public class NetworkCheck {

    public boolean getConnectionStatus(Context context) {
        NetworkInfo networkInfo = null;
        boolean isConnected = false;

        try {

            ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = conManager.getActiveNetworkInfo();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            if (networkInfo != null) {
                Log.i("Network info", networkInfo.getState().toString());
                isConnected = networkInfo.isConnected();
            }
        }

        return isConnected;
    }

}
