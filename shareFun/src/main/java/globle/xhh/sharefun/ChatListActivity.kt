package globle.xhh.sharefun

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import globle.xhh.sharefun.notification.event.MessageLoadSuccess
import org.drinkless.td.libcore.telegram.TdApi
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 *2022/6/17
 *@author zhangxiaohui
 *@describe
 */
class ChatListActivity : FragmentActivity() {
    private val msgAdapter by lazy {
        ChatListAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_list_layout)

        EventBus.getDefault().register(this)

        val chatList = findViewById<RecyclerView>(R.id.chatList)
        chatList.adapter = msgAdapter

        ClientManager.get().getChatList()
    }

    @Subscribe
    fun messageLoad(message: MessageLoadSuccess) {
        ClientManager.get().getChatMessageFromLocal(message.chatId) { list ->
            val msg = list.first()
            val msgText = (msg.content as? TdApi.MessageText)?.text?.toString()
            val time = msg.date.toString()
            val chatEntity = AdapterChatsEntity("", msgText, time)
            runOnUiThread {
                msgAdapter.bindData(false, arrayListOf(chatEntity))
            }
        }
    }
}