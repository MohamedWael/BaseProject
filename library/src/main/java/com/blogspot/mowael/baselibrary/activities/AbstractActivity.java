package com.blogspot.mowael.baselibrary.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;

import com.blogspot.mowael.baselibrary.contract.Contract;
import com.blogspot.mowael.utilslibrary.network.NetworkStateReceiver;


/**
 * Created by mohamed wael on 1/1/2018.
 */

public class AbstractActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener, Contract.AbstractView {
    private NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkStateReceiver = NetworkStateReceiver.newInstance();
        networkStateReceiver.addListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, networkStateReceiver.getIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkStateReceiver);
    }

    @Override
    protected void onDestroy() {
        if (networkStateReceiver != null) {
            networkStateReceiver.onDestroy();
        }
        networkStateReceiver = null;
        super.onDestroy();
    }

    @Override
    public void networkAvailable() {

    }

    @Override
    public void networkUnavailable() {

    }

    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * the tag is set to class name
     * isAddToBackStack is set to true
     *
     * @param fragment the target fragment
     * @param in       the layout to open fragment in it
     * @param <T>      any fragment that extend the Fragment
     */
    public <T extends Fragment> void loadFragment(T fragment, @IdRes int in) {
        loadFragment(fragment, in, true);
    }


    /**
     * the tag is set to class name
     *
     * @param fragment         the target fragment
     * @param in               the layout to open fragment in it
     * @param isAddToBackStack whether to add the target fragment to backStack or not
     * @param <T>              any fragment that extend the Fragment
     */
    public <T extends Fragment> void loadFragment(T fragment, @IdRes int in, boolean isAddToBackStack) {
        loadFragment(fragment, in, fragment.getClass().getSimpleName(), isAddToBackStack);
    }


    /**
     * load T fragment in the required resource in identified with the Tag tag
     *
     * @param fragment         the target fragment
     * @param in               the layout to open fragment in it
     * @param tag              the tag used to identify fragment
     * @param isAddToBackStack whether to add the target fragment to backStack or not
     * @param <T>              any fragment that extend the android.support.v4.app.Fragment
     */
    public <T extends Fragment> void loadFragment(T fragment, @IdRes int in, String tag, boolean isAddToBackStack) {
        if (getFragmentManager() == null) return;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(in, fragment, tag).commit();
    }


    public void startActivity(Context context, Class<? extends Activity> aClass, @Nullable Bundle extras, boolean finish) {
        Intent intent = new Intent(context, aClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
        if (finish) ((Activity) context).finish();
    }

    /**
     * start the required Activity and finish the origin
     *
     * @param aClass
     */
    public void startActivity(Class<? extends Activity> aClass) {
        startActivity(aClass, true);
    }

    public void startActivity(Class<? extends Activity> aClass, boolean finish) {
        startActivity(this, aClass, null, finish);
    }

    public void startActivity(Class<? extends Activity> aClass, Bundle extras, boolean finish) {
        startActivity(this, aClass, extras, finish);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass, boolean finish) {
        startActivity(context, aClass, null, finish);
    }

    public void startActivity(Context context, Class<? extends Activity> aClass) {
        startActivity(context, aClass, null, false);
    }

    public void restrictToPortait() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
