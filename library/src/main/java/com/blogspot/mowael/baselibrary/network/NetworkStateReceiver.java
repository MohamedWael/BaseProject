package com.blogspot.mowael.baselibrary.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moham on 11/21/2016.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    protected List<NetworkStateReceiverListener> listeners;
    protected Boolean connected;
    private static NetworkStateReceiver instance;
    private IntentFilter intentFilter;

    public static NetworkStateReceiver newInstance() {
        instance = new NetworkStateReceiver();
        return instance;
    }

    private NetworkStateReceiver() {
        listeners = new ArrayList<NetworkStateReceiverListener>();
        connected = null;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getExtras() == null)
            return;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();

        if (isConnectingToInternet(context)) {
            connected = true;
        } else {
            connected = false;
        }
//        if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
//            connected = true;
//        } else if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
//            connected = false;
//        }

        notifyStateToAll();
    }

    private void notifyStateToAll() {
        if (listeners != null)
            for (NetworkStateReceiverListener listener : listeners) {
                if (listener != null)
                    notifyState(listener);
            }
    }

    private void notifyState(NetworkStateReceiverListener listener) {
        if (connected == null || listener == null)
            return;

        if (connected == true)
            listener.networkAvailable();
        else
            listener.networkUnavailable();
    }

    public void addListener(NetworkStateReceiverListener l) {
        listeners.add(l);
        notifyState(l);
    }

    public void removeListener(NetworkStateReceiverListener l) {
        listeners.remove(l);
    }

    public boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public IntentFilter getIntentFilter() {
        if (intentFilter == null) {
            intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        }
        return intentFilter;
    }

    public void onDestroy() {
        listeners = null;
        connected = null;
        instance = null;
        intentFilter = null;
    }

    public interface NetworkStateReceiverListener {
        void networkAvailable();

        void networkUnavailable();
    }
}
