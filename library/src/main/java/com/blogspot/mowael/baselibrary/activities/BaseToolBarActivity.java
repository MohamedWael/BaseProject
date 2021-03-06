package com.blogspot.mowael.baselibrary.activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.blogspot.mowael.baselibrary.R;
import com.blogspot.mowael.baselibrary.widgets.InnerNotificationView;

/**
 * Created by moham on 12/26/2017.
 */

public class BaseToolBarActivity extends BaseActivity {
    protected InnerNotificationView invNotification;
    protected FrameLayout flContentView;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_toolbar);

        flContentView = findViewById(R.id.flContentView);
        invNotification = findViewById(R.id.invNotification);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View view = getLayoutInflater().inflate(layoutResID, null);
        flContentView.removeAllViews();
        flContentView.addView(view);
    }

    @Override
    public void setContentView(View view) {
        flContentView.removeAllViews();
        flContentView.addView(view);
    }

    /**
     * add back arrow to toolbar
     */
    public void enableBackButton() {
        enableBackButton(true);
    }

    /**
     * add back arrow to toolbar
     */
    public void enableBackButton(boolean enable) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
            getSupportActionBar().setDisplayShowHomeEnabled(enable);
        }
    }

    /**
     * enables the Default Toolbar with back button
     *
     * @param listener the Click listener of the back button associated with the toolbar
     */
    public void enableBackButton(View.OnClickListener listener) {
        enableBackButton(listener, null);
    }

    /**
     * enables the Default Toolbar with back button
     *
     * @param listener       the Click listener of the back button associated with the toolbar
     * @param navigationIcon the Navigation Icon
     */
    public void enableBackButton(View.OnClickListener listener, @Nullable Drawable navigationIcon) {
        if (navigationIcon != null) {
            toolbar.setNavigationIcon(navigationIcon);
        }
        enableBackButton();
        toolbar.setNavigationOnClickListener(listener);
    }

    /**
     * enables the Default Toolbar with back button
     *
     * @param listener       the Click listener of the back button associated with the toolbar
     * @param navigationIcon the Navigation Icon
     */
    public void enableBackButton(View.OnClickListener listener, @DrawableRes int navigationIcon) {
        if (navigationIcon != -1) {
            toolbar.setNavigationIcon(navigationIcon);
        }
        enableBackButton();
        toolbar.setNavigationOnClickListener(listener);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Change toolbar navigation color filter
     *
     * @param id          navigation icon color
     * @param colorFilter color filter
     */
    public void setNavigationIconColorFilter(@ColorRes int id, PorterDuff.Mode colorFilter) {
        getToolbar().getNavigationIcon().setColorFilter(
                ContextCompat.getColor(this, id), colorFilter);
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
        loadFragment(fragment, R.id.flContentView, isAddToBackStack);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        invNotification = null;
        flContentView = null;
    }
}
