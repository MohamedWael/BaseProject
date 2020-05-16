package com.blogspot.mowael.baselibrary.vo

import com.blogspot.mowael.baselibrary.contract.ErrorMessageHandler

data class ViewMessage(
        var msg: String? = null,
        var messageResource: MessageResource? = null,
        var errorMessageHandler: ErrorMessageHandler? = null
)

data class MessageResource(val msgRes: Int, val args: Array<out Any>? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MessageResource

        if (msgRes != other.msgRes) return false
        if (args != null) {
            if (other.args == null) return false
            if (!args.contentEquals(other.args!!)) return false
        } else if (other.args != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = msgRes ?: 0
        result = 31 * result + (args?.contentHashCode() ?: 0)
        return result
    }
}