package globle.xhh.sharefun

import android.util.Log
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi

/**
 *2022/6/13
 *@author zhangxiaohui
 *@describe
 */
open class ClientManager {
    private var mClient: Client? = null

    fun init() {
        val updateHandler = UpdateHandler()
        mClient = Client.create(updateHandler, null, null)
        updateHandler.client = mClient
    }

    fun sendPhoneNumberVerificationCode(){
        mClient!!.send(
            TdApi.SendPhoneNumberVerificationCode(
            "+8618328717019", TdApi.PhoneNumberAuthenticationSettings(
                false, false, false,
                false, emptyArray()
            )
        )) { result ->
            Log.d("", "SetTdlibParameters${result.toString()}")
        }
    }
}