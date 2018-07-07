package com.blogspot.mowael.baselibrary.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by moham on 4/30/2017.
 */

public class MoPermission {

    public MoPermission() {

    }

    /**
     * request array of permissions if and only if Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    public static void requestGroupOfPermissions(Activity activity, @NonNull String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    /**
     * request array of permission if and only if Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
     *
     * @param activity
     * @param permission
     * @param requestCode
     */
    public static void requestPermission(Activity activity, @NonNull String permission, int requestCode) {
        requestGroupOfPermissions(activity, new String[]{permission}, requestCode);
    }

    /**
     * @param mContext
     * @param permission
     * @return true if the permission is not granted otherwise false
     */
    public static boolean checkIfDenied(Context mContext, String permission) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED;
    }

    /**
     * @param activity
     * @param permission
     * @param requestCode
     * @return true if the permission is not granted otherwise false
     */
    public static boolean checkAndRequestPermission(Activity activity, String permission, int requestCode) {
        if (checkIfDenied(activity, permission)) {
            requestPermission(activity, permission, requestCode);
            return true;
        }
        return false;
    }

    /**
     * works only if and only if Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
     *
     * @param activity
     * @param permissions list on requested permissions
     * @param requestCode permissions request code
     * @return true if the permission is not granted otherwise false
     */
    public static void checkAndRequestGroupPermission(Activity activity, String[] permissions, int requestCode) {
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (checkIfDenied(activity, permission)) {
                deniedPermissions.add(permission);
            }
        }
        if (deniedPermissions.size() > 0)
            requestGroupOfPermissions(activity, deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
    }
}
