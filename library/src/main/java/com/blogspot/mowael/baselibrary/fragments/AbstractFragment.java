package com.blogspot.mowael.baselibrary.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.blogspot.mowael.baselibrary.R;
import com.blogspot.mowael.baselibrary.contract.Contract;

/**
 * Created by moham on 1/1/2018.
 */

public class AbstractFragment extends Fragment implements Contract.AbstractView {

    /**
     * start the required Activity and finish the origin
     *
     * @param aClass
     */
    public void startActivity(Class<? extends Activity> aClass) {
        startActivity(aClass, true);
    }

    public void startActivity(Class<? extends Activity> aClass, boolean finish) {
        startActivity(aClass, null, finish);
    }

    public void startActivity(Class<? extends Activity> aClass, @Nullable Bundle extras, boolean finish) {
        Intent intent = new Intent(getActivity(), aClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        startActivity(intent);
        if (finish) getActivity().finish();
    }


    public <T extends Fragment> void loadFragment(T fragment, @IdRes int in, String tag, boolean isAddToBackStack) {
        if (getParentFragmentManager() == null) return;
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.replace(in, fragment, tag).commit();
    }

    public <T extends Fragment> void loadFragment(T fragment, @IdRes int in, boolean isAddToBackStack) {
        loadFragment(fragment, in, fragment.getClass().getSimpleName(), isAddToBackStack);
    }

    /**
     * load fragment in the provided layout and add it to the backStack
     *
     * @param fragment
     * @param in
     * @param <T>
     */
    public <T extends Fragment> void loadFragment(T fragment, @IdRes int in) {
        loadFragment(fragment, in, true);
    }

    protected void finish() {
        getActivity().finish();
    }

    public boolean isRunning() {
        FragmentActivity activity = getActivity();
        return activity != null && isAdded();
    }


    public void showSnakeMessage(View anchor, String msg) {
        if (anchor != null) {
            final Snackbar snackbar = Snackbar.make(anchor, msg, Snackbar.LENGTH_SHORT);
            snackbar.setAction(R.string.cancel, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        } else {
            if (!TextUtils.isEmpty(msg))
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void showSnakeMessage(String msg) {
        showSnakeMessage(getView(), msg);
    }


    @TargetApi(Build.VERSION_CODES.M)
    boolean isGranted(String permission) {
        return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
