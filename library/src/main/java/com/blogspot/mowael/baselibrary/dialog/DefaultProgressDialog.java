package com.blogspot.mowael.baselibrary.dialog;

import android.app.Activity;
import androidx.appcompat.app.AlertDialog;

import com.blogspot.mowael.baselibrary.R;


/**
 * Created by moham on 5/27/2017.
 */

public class DefaultProgressDialog implements ProgressDialog {
    private static DefaultProgressDialog instance;
    private AlertDialog dialog;

    public static DefaultProgressDialog newInstance(Activity activity) {
        return new DefaultProgressDialog(activity);
    }

    protected DefaultProgressDialog(Activity activity) {
        initDialog(activity);
    }

    protected void initDialog(Activity activity) {
        dialog = new AlertDialog.Builder(activity).setView(R.layout.loading_layout).setCancelable(false).create();
    }

    @Override
    public void show() {
        dialog.show();
    }

    @Override
    public void dismiss() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public boolean isShowing() {
        return dialog.isShowing();
    }

    public AlertDialog getDialog() {
        return dialog;
    }
}
