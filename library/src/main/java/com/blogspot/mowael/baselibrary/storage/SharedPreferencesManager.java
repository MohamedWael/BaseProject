package com.blogspot.mowael.baselibrary.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.blogspot.mowael.baselibrary.utils.Config;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by moham on 2/1/2017.
 */
public class SharedPreferencesManager {
    private static SharedPreferencesManager ourInstance;
    private Context mContext;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    private SharedPreferencesManager() {
    }

    public static SharedPreferencesManager getInstance() {
        if (ourInstance == null) ourInstance = new SharedPreferencesManager();
        return ourInstance;
    }

    private Context getContext() {
        if (mContext == null) {
            throw new RuntimeException("you must setContext() in order to initialize Shared Prereference");
        }
        return mContext;
    }

    public SharedPreferencesManager setContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public SharedPreferences initSharedPreferences(Context mContext, String sharedPrefrencesName) {
        setContext(mContext);
        return prefs = getContext().getSharedPreferences(sharedPrefrencesName, MODE_PRIVATE);
    }

    public SharedPreferences initSharedPreferences(Context mContext) {
        return initSharedPreferences(mContext, Config.SHARED_PREFERENCES_NAME);
    }

    public SharedPreferences initSharedPreferences() {
        return initSharedPreferences(getContext());
    }

    public SharedPreferences.Editor initEditor(Context mContext) {
        setContext(mContext);
        if (prefs == null) {
            initSharedPreferences(mContext);
        }
        return editor = prefs.edit();
    }

    public SharedPreferences.Editor initEditor() {
        return initEditor(getContext());
    }

    /**
     * Returns true if the new values were successfully written to persistent storage
     * or returns false if editor is null or the values weren't written to persistent storage
     */
    public boolean closeEditor() {
        if (editor != null) return editor.commit();
        return false;
    }

    public SharedPreferences.Editor getEditor() {
        if (editor == null) {
            return initEditor();
        }
        return editor;
    }

    public SharedPreferences getPrefs() {
        if (prefs == null) {
            return initSharedPreferences();
        }
        return prefs;
    }
}
