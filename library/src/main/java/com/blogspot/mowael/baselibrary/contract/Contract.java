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

        /**
         * Deprecated, use showMessage() instead
         * @param msg
         */
        @Deprecated
        void showSnakeMessage(String msg);

        /**
         * Deprecated, use showMessage() instead
         * @param msgRes
         */
        @Deprecated
        void showSnakeMessage(int msgRes);

        /**
         * Deprecated, use showMessage() instead
         * @param msgRes
         * @param args
         */
        @Deprecated
        void showSnakeMessage(int msgRes, Object... args);

        /**
         * Deprecated, use showMessage() instead
         * @param errorMessageHandler
         */
        @Deprecated
        void showSnakeMessage(ErrorMessageHandler errorMessageHandler);

        void showMessage(String msg);

        void showMessage(int msgRes);

        void showMessage(int msgRes, Object... args);

        void showMessage(ErrorMessageHandler errorMessageHandler);
    }

    interface AbstractViewModel {
    }

    interface BaseViewModel extends AbstractViewModel {

        void setView(AbstractView view);
    }


}
