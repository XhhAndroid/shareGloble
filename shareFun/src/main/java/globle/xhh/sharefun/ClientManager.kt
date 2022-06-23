package globle.xhh.sharefun

import android.os.Handler
import android.util.Log
import android.view.ActionMode
import globle.xhh.sharefun.notification.event.MessageLoadSuccess
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.*
import org.greenrobot.eventbus.EventBus
import java.util.function.Consumer
import javax.security.auth.callback.Callback

/**
 *2022/6/13
 *@author zhangxiaohui
 *@author zhangxiaohui
 *@describe
 */
open class ClientManager private constructor() {
    companion object {
        var instance: ClientManager? = null
        fun get(): ClientManager {
            if (instance == null) {
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

    fun getChatList() {
        mClient!!.send(TdApi.GetChats(ChatListMain(), 100)) {
            for (chatId in (it as Chats).chatIds) {
                mClient!!.send(GetChatHistory(chatId, 0, -1, 2, false)) { i ->
                    if (i != null) {
                        EventBus.getDefault().post(MessageLoadSuccess(chatId))
                    }
                }
            }
        }
    }

    fun getChatMessageFromLocal(chatId: Long,callBack: Consumer<List<Message>>) {
        mClient!!.send(GetChatHistory(chatId, 0, -1, 2, true)) { i ->
            if (i != null) {
                callBack.accept((i as Messages).messages.toList())
            }
        }
    }
}