package com.blogspot.mowael.baselibrary.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blogspot.mowael.baselibrary.R;

/**
 * Created by moham on 12/5/2017.
 */

public class InnerNotificationView extends LinearLayout {

    private ImageView ivErrorIcon, ivCancel;
    private TextView tvErrorMsg;
    private Button btnAction;


    private OnNotificationDismissListener onNotificationDismissListener;
    private LinearLayout llNotificationRoot;

    public InnerNotificationView(Context context) {
        super(context);
        initView(context);
    }

    public InnerNotificationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public InnerNotificationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InnerNotificationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_inner_notification, this);
        bindViews();
    }

    private void bindViews() {
        llNotificationRoot = findViewById(R.id.llNotificationRoot);
        ivCancel = findViewById(R.id.ivCancel);
        ivErrorIcon = findViewById(R.id.ivErrorIcon);
        tvErrorMsg = findViewById(R.id.tvErrorMsg);
        btnAction = findViewById(R.id.btnAction);
    }

    public void composeNotification(Drawable errorIcon, String errorRes, @Nullable OnClickListener actionClickListener) {
        btnAction.setVisibility(actionClickListener != null ? VISIBLE : GONE);
        btnAction.setOnClickListener(actionClickListener);

        if (errorIcon != null) {
            ivErrorIcon.setImageDrawable(errorIcon);
        }

        if (errorRes != null) {
            tvErrorMsg.setText(errorRes);
        }
    }

    public void composeNotification(@DrawableRes int errorIcon, @StringRes int errorRes, @Nullable OnClickListener actionClickListener) {
        composeNotification(errorIcon != -1 ? ContextCompat.getDrawable(getContext(), errorIcon) : null,
                errorRes != -1 ? getContext().getString(errorRes) : null, actionClickListener);
    }

    public void composeNotification(String errorRes, @Nullable OnClickListener actionClickListener) {
        composeNotification(null, errorRes, actionClickListener);
    }

    public void composeNotification(String errorRes) {
        composeNotification(errorRes, null);
    }

    public void composeNotification(@StringRes int errorRes, @Nullable OnClickListener actionClickListener) {
        composeNotification(-1, errorRes, actionClickListener);
    }

    public void composeNotification(@StringRes int errorRes) {
        composeNotification(errorRes, null);
    }

    public void setOnNotificationDismissListener(OnNotificationDismissListener onNotificationDismissListener) {
        this.onNotificationDismissListener = onNotificationDismissListener;
    }

    public void addExtentionView(View view){
        llNotificationRoot.addView(view);
    }

    public void setCancellable(boolean isCancellable) {
        ivCancel.setVisibility(isCancellable ? VISIBLE : GONE);
        if (isCancellable) {
            ivCancel.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onNotificationDismissListener != null)
                        onNotificationDismissListener.onDismiss(InnerNotificationView.this);
                    InnerNotificationView.this.setVisibility(GONE);
                }
            });
        } else {
            ivCancel.setOnClickListener(null);
        }
    }

    public interface OnNotificationDismissListener {
        void onDismiss(InnerNotificationView view);
    }
}
