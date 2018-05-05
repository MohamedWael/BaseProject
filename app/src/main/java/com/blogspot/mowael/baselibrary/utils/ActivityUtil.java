package com.blogspot.mowael.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

public class ActivityUtil {

    /**
     * Extract the Activity from TintContextWrapper
     * Since Support library version 23.3.0 View.getContext() returns a TintContextWrapper object instead of an Activity.
     *
     * @param context any context
     * @return extracted Activity form the TintContextWrapper
     */
    public static Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
