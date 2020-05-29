package com.blogspot.mowael.baselibrary.viewModel

import androidx.lifecycle.MutableLiveData
import com.blogspot.mowael.baselibrary.contract.ErrorMessageHandler
import com.blogspot.mowael.baselibrary.vo.MessageResource
import com.blogspot.mowael.baselibrary.vo.ViewMessage
import com.blogspot.mowael.utilslibrary.utils.SingleLiveDataEvent

open class ModernViewModel : AbstractViewModel() {

    open val viewMessageLiveData: MutableLiveData<SingleLiveDataEvent<ViewMessage>> = MutableLiveData<SingleLiveDataEvent<ViewMessage>>()
    open val progressLiveData: MutableLiveData<SingleLiveDataEvent<Boolean>> = MutableLiveData<SingleLiveDataEvent<Boolean>>()
    open val progressDialogLiveData: MutableLiveData<SingleLiveDataEvent<Boolean>> = MutableLiveData<SingleLiveDataEvent<Boolean>>()

    open fun showProgress(show: Boolean) {
        progressLiveData.value = SingleLiveDataEvent(show)
    }

    open fun showProgressDialog(show: Boolean) {
        progressDialogLiveData.value = SingleLiveDataEvent(show)
    }

    open fun showMessage(msg: String) {
        viewMessageLiveData.value = SingleLiveDataEvent(ViewMessage(msg))
    }

    open fun showMessage(msgRes: Int) {
        viewMessageLiveData.value = SingleLiveDataEvent(ViewMessage(messageResource = MessageResource(msgRes)))
    }

    open fun showMessage(msgRes: Int, vararg args: Any) {
        viewMessageLiveData.value = SingleLiveDataEvent(ViewMessage(messageResource = MessageResource(msgRes, args)))
    }

    open fun showMessage(errorMessageHandler: ErrorMessageHandler) {
        viewMessageLiveData.value = SingleLiveDataEvent(ViewMessage(errorMessageHandler = errorMessageHandler))
    }

}