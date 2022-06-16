package globle.xhh.sharefun

import android.os.Handler
import android.util.Log
import android.view.ActionMode
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.*
import java.util.function.Consumer
import javax.security.auth.callback.Callback

/**
 *2022/6/13
 *@author zhangxiaohui
 *@author zhangxiaohui
 *@describe
 */
open class ClientManager private constructor(){
    companion object {
        var instance: ClientManager? = null
        fun get():ClientManager{
            if(instance == null){
                instance = ClientManager()
            }
            return instance!!
        }
    }

    private var mClient: Client? = null

    fun init() {
        val updateHandler = UpdateHandler()
        mClient = Client.create(updateHandler, null, null)
        updateHandler.client = mClient
    }

    fun getAuthorizationState(callBack: Consumer<String>) {
        mClient!!.send(TdApi.GetAuthorizationState(), object : Client.ResultHandler {
            override fun onResult(obj: TdApi.Object?) {
                callBack.accept(obj.toString())
            }
        })
    }

    val phoneNumber = "008618123342517"
//    "9996613324"

    fun sendPhoneCode() {
        mClient!!.send(TdApi.SetAuthenticationPhoneNumber(phoneNumber, null), UpdateHandler.AuthorizationRequestHandler());
    }

    fun loginTg(code: String) {
        mClient!!.send(TdApi.CheckAuthenticationCode(code), UpdateHandler.AuthorizationRequestHandler())
    }

    fun getChatList(callBack: Consumer<LongArray>) {
        mClient!!.send(TdApi.GetChats(ChatListMain(), 100), {
            callBack.accept((it as Chats).chatIds)
        })
    }
}