package com.blogspot.mowael.baselibrary.contract;

/**
 * Created by moham on 12/3/2017.
 */

public interface Contract {

    interface AbstractView {
    }

    interface BaseView extends AbstractView {

        void showProgress(boolean show);

        void showProgressDialog();

        void hideProgressDialog();

        void showSnakeMessage(String msg);

        void showSnakeMessage(int msgRes);

        void showSnakeMessage(int msgRes, Object... args);

        void showSnakeMessage(ErrorMessageHandler errorMessageHandler);

    }

    interface AbstractViewModel {
    }

    interface BaseViewModel extends AbstractViewModel {

        void setView(BaseView view);
    }


}
