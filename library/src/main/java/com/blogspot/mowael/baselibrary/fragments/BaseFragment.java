package com.blogspot.mowael.baselibrary.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blogspot.mowael.baselibrary.R;
import com.blogspot.mowael.baselibrary.activities.BaseActivity;
import com.blogspot.mowael.baselibrary.activities.BaseToolBarActivity;
import com.blogspot.mowael.baselibrary.contract.Contract;
import com.blogspot.mowael.baselibrary.contract.ErrorMessageHandler;
import com.blogspot.mowael.baselibrary.viewModel.ModernViewModel;
import com.blogspot.mowael.baselibrary.vo.ViewMessage;
import com.blogspot.mowael.utilslibrary.Logger;

/**
 * Created by moham on 12/26/2017.
 */

public class BaseFragment extends AbstractFragment implements SwipeRefreshLayout.OnRefreshListener, Contract.BaseView {

    private View rootView;
    private ProgressBar pbProgress;
    private LinearLayout rlFragmentRoot;
    private SwipeRefreshLayout srlRoot;
    private LinearLayout llBlockView;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_base, container, false);
        bindViewToRootView(rootView);
        setRefreshing(false);
        showProgress(false);
        return rootView;
    }

    public <T extends ViewModel> T createViewModel(Class<T> viewModelClass) {
        return createViewModel(viewModelClass, null);
    }

    public <T extends ViewModel> T createViewModel(Class<T> viewModelClass, ViewModelProvider.Factory factory) {
        T viewModel;
        if (factory != null) {
            viewModel = new ViewModelProvider(this, factory).get(viewModelClass);
        } else {
            viewModel = new ViewModelProvider(this).get(viewModelClass);
        }
        if (viewModel instanceof Contract.BaseViewModel)
            setUpViewModel((Contract.BaseViewModel) viewModel);
        if (viewModel instanceof ModernViewModel) setupModernViewModel((ModernViewModel) viewModel);
        return viewModel;
    }

    public void setupModernViewModel(ModernViewModel viewModel) {
        viewModel.getProgressDialogLiveData().observe(getViewLifecycleOwner(), booleanSingleLiveDataEvent -> {
            Boolean showProgress = booleanSingleLiveDataEvent.getContentIfNotHandled();
            if (showProgress != null) {
                if (showProgress) {
                    showProgressDialog();
                } else {
                    hideProgressDialog();
                }
            }
        });
        viewModel.getProgressLiveData().observe(getViewLifecycleOwner(), booleanSingleLiveDataEvent -> {
            Boolean showProgress = booleanSingleLiveDataEvent.getContentIfNotHandled();
            if (showProgress != null) showProgress(showProgress);
        });

        viewModel.getViewMessageLiveData().observe(getViewLifecycleOwner(), viewMessageSingleLiveDataEvent -> {
            ViewMessage viewMessage = viewMessageSingleLiveDataEvent.getContentIfNotHandled();
            if (viewMessage != null) handleViewMessage(viewMessage);
        });
    }

    public void handleViewMessage(ViewMessage viewMessage) {
        if (viewMessage.getErrorMessageHandler() != null) {
            showMessage(viewMessage.getErrorMessageHandler());
        } else if (viewMessage.getMsg() != null) {
            showMessage(viewMessage.getMsg());
        } else if (viewMessage.getMessageResource() != null) {
            showMessage(viewMessage.getMessageResource().getMsgRes(), viewMessage.getMessageResource().getArgs());
        } else {
            Logger.d("cannot display message!");
        }
    }

    public void setUpViewModel(Contract.BaseViewModel viewModel) {
        viewModel.setView(this);
    }

    /**
     * @param rootView
     */
    protected void bindViewToRootView(View rootView) {
        srlRoot = (SwipeRefreshLayout) rootView.findViewById(R.id.srlRoot);
        rlFragmentRoot = (LinearLayout) rootView.findViewById(R.id.rlFragmentRoot);
        pbProgress = (ProgressBar) rootView.findViewById(R.id.pbProgress);
        llBlockView = (LinearLayout) rootView.findViewById(R.id.llBlockView);
        enableSwipeRefresh(false);
        srlRoot.setOnRefreshListener(this);
        llBlockView.setVisibility(View.GONE);
    }


    public void setRefreshing(boolean refreshing) {
        if (srlRoot != null) {
            srlRoot.setRefreshing(refreshing);
        }
    }

    public boolean isRefreshing() {
        if (srlRoot != null)
            return srlRoot.isRefreshing();
        return false;
    }

    public void enableSwipeRefresh(boolean refreshing) {
        if (srlRoot != null)
            srlRoot.setEnabled(refreshing);
    }

    /**
     * BlockView is linearLayout with vertical orientation on top of the FragmentRoot relativeLayout
     *
     * @param view to be added on top of fragment
     */
    public void addBlockView(View view) {
        if (llBlockView != null)
            llBlockView.addView(view);
    }

    public void showBlockView(boolean show) {
        if (llBlockView != null)
            llBlockView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * You have to call on super.onCreateView() in your onCreateView before inflating your View
     *
     * @return the root view inflated with the MoFragment
     */
    @Nullable
    public View attachToRootView(View view) {
        if (rootView == null)
            throwBaseFragmentException();
        rlFragmentRoot.addView(view);
        return rootView;
    }

    /**
     * @return the root progress bar attached in the root view
     */
    @Nullable
    protected ProgressBar getRootProgressBar() {
        return pbProgress;
    }

    protected void showRootProgress(boolean show) {
        if (getRootProgressBar() != null) {
            getRootProgressBar().setVisibility(show ? View.VISIBLE : View.GONE);
        } else {
            Logger.e("getRootProgressBar()", "is null");
        }
    }


    public <T extends Fragment> void loadFragment(T fragment, boolean isAddToBackStack) {
        int fragmentContainer = 0;
        if (getActivity() instanceof BaseActivity)
            fragmentContainer = R.id.flRootContentView;
        else if (getActivity() instanceof BaseToolBarActivity)
            fragmentContainer = R.id.flContentView;
        else
            throw new IllegalStateException("No fragment container as you are not using BaseActivity or BaseToolBarActivity");
        loadFragment(fragment, fragmentContainer, isAddToBackStack);
    }

    /**
     * it adds the fragment to backStack by default.
     *
     * @param fragment
     * @param <T>
     */
    public <T extends Fragment> void loadFragment(T fragment) {
        loadFragment(fragment, true);
    }

    private void throwBaseFragmentException() {
        throw new RuntimeException("You have to call on super.onCreateView() in your onCreateView before inflating your View");
    }

    @Override
    public void onRefresh() {
        // not implemented
    }

    @Override
    public void showProgress(boolean show) {
        showRootProgress(show);
    }

    @Override
    public void showProgressDialog() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).hideProgressDialog();
    }

    @Override
    public void showSnakeMessage(String msg) {
        showMessage(msg);
    }

    @Override
    public void showSnakeMessage(int msgRes) {
        showMessage(msgRes);
    }

    @Override
    public void showSnakeMessage(int msgRes, Object... args) {
        showMessage(msgRes, args);
    }

    @Override
    public void showSnakeMessage(ErrorMessageHandler errorMessageHandler) {
        showMessage(errorMessageHandler);
    }

    @Override
    public void showMessage(String msg) {
        super.showMessage(msg);
    }

    @Override
    public void showMessage(int msgRes) {
        showMessage(getString(msgRes));
    }

    @Override
    public void showMessage(int msgRes, Object... args) {
        if (args == null) showMessage(msgRes);
        else showMessage(getString(msgRes, args));
    }

    @Override
    public void showMessage(ErrorMessageHandler errorMessageHandler) {
        if (errorMessageHandler != null) {
            if (!TextUtils.isEmpty(errorMessageHandler.getMessage()))
                showSnakeMessage(errorMessageHandler.getMessage());
            else {
                if (errorMessageHandler.getMessageRes() != 0)
                    showSnakeMessage(errorMessageHandler.getMessageRes());
            }
        }
    }


    @Override
    public void onDestroy() {
        if (pbProgress != null) pbProgress = null;
        if (rlFragmentRoot != null) rlFragmentRoot = null;
        if (srlRoot != null) srlRoot = null;
        if (llBlockView != null) llBlockView = null;
        if (rootView != null) rootView = null;
        super.onDestroy();
    }
}
