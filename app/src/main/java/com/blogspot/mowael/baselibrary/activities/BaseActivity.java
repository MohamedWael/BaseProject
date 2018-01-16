package com.blogspot.mowael.baselibrary.activities;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;

import com.blogspot.mowael.baselibrary.R;
import com.blogspot.mowael.baselibrary.dialog.DefaultProgressDialog;
import com.blogspot.mowael.baselibrary.dialog.ProgressDialog;

/**
 * Created by moham on 12/26/2017.
 */

public class BaseActivity extends AbstractActivity {


    protected ProgressDialog progressDialog;
    protected FrameLayout flRootContentView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);

        flRootContentView = findViewById(R.id.flRootContentView);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        flRootContentView.removeAllViews();
        flRootContentView.addView(view);
    }

    @Override
    public void setContentView(View view) {
        flRootContentView.removeAllViews();
        flRootContentView.addView(view);
    }

    public ProgressDialog getProgressDialog() {
        if (progressDialog == null) {
            progressDialog = DefaultProgressDialog.newInstance(this);
        }
        return progressDialog;
    }

    public void showProgressDialog() {
        getProgressDialog().show();
    }

    public void hideProgressDialog() {
        getProgressDialog().dismiss();
    }


    @Override
    protected void onDestroy() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = null;
        if (flRootContentView != null) flRootContentView.removeAllViews();
        flRootContentView = null;
        super.onDestroy();
    }


    /**
     * load fragment in the default container of the MoActivity
     * <p>
     * the tag is set to class name
     * in is set to default fragment container in the MoActivity
     *
     * @param fragment         the target fragment
     * @param isAddToBackStack whether to add the target fragment to backStack or not
     * @param <T>              any fragment that extend the Fragment
     */
    public <T extends Fragment> void loadFragment(T fragment, boolean isAddToBackStack) {
        loadFragment(fragment, R.id.flRootContentView, isAddToBackStack);
    }

    /**
     * load fragment in the default container of the MoActivity
     * <p>
     * the tag is set to class name
     * isAddToBackStack is set to false
     *
     * @param fragment the target fragment
     * @param <T>      any fragment that extend the Fragment
     */
    public <T extends Fragment> void loadFragment(T fragment) {
        loadFragment(fragment, false);
    }

}
