package com.blogspot.mowael.baselibrary.utils;

import android.util.Log;

/**
 * Created by moham on 1/23/2017.
 */

public final class Logger {

    private Logger() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    public static void d(String tag, String message) {
        if (Config.LOG_TOGGLE)
            Log.d(tag, getMsg(message));
    }

    public static void e(String tag, String message) {
        if (Config.LOG_TOGGLE)
            Log.e(tag, getMsg(message));
    }

    public static void i(String tag, String message) {
        if (Config.LOG_TOGGLE)
            Log.i(tag, getMsg(message));
    }

    public static void w(String tag, String message) {
        if (Config.LOG_TOGGLE)
            Log.w(tag, getMsg(message));
    }

    public static void d(String message) {
        d(getClassName(), message);
    }

    public static void e(String message) {
        e(getClassName(), message);
    }

    public static void i(String message) {
        i(getClassName(), message);
    }

    public static void w(String message) {
        w(getClassName(), message);
    }

    private static String getClassName() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        StackTraceElement relevantTrace = trace[4];
        String className = relevantTrace.getClassName();
        int lastIndex = className.lastIndexOf('.');
        return className.substring(lastIndex + 1);
    }

    private static String getMsg(String msg) {
        if (msg != null) return msg;
        else return "the message you entered is empty or null";
    }
}
