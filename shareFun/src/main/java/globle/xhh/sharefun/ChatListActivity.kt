package globle.xhh.sharefun

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

/**
 *2022/6/17
 *@author zhangxiaohui
 *@describe
 */
class ChatListActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_list_layout)

        val chatList = findViewById<TextView>(R.id.chatList)
        var chatIds : String = ""
        ClientManager.get().getChatList { array ->
            for (a in array) {
                chatIds += "\n$a"
            }
            runOnUiThread {
                chatList.text = chatIds
            }
        }
    }
}