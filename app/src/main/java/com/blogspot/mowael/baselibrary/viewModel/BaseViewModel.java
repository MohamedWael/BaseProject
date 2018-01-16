package com.blogspot.mowael.baselibrary.viewModel;


import com.blogspot.mowael.baselibrary.contract.Contract;

/**
 * Created by moham on 1/3/2018.
 */

public class BaseViewModel<V extends Contract.AbstractView> extends AbstractViewModel implements Contract.BaseViewModel {

    private V view;

    public BaseViewModel() {
    }

    public BaseViewModel(V view) {
        this.view = view;
        onViewAdded(this.view);
    }

    @Override
    public void setView(Contract.BaseView view) {
        this.view = (V) view;
        onViewAdded(this.view);
    }

    protected void onViewAdded(V view) {

    }

    public V getView() {
        if (view == null) throw new NullPointerException("you have to setView in your view");
        return view;
    }

    @Override
    protected void onCleared() {
        view = null;
        super.onCleared();
    }
}
