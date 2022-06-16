package globle.xhh.share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import globle.xhh.base.Config
import globle.xhh.sharefun.ChatListActivity
import globle.xhh.sharefun.ClientManager
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ClientManager.get().init()
        ClientManager.get().getAuthorizationState{
            findViewById<TextView>(R.id.stateTv).text = it
        }
        findViewById<Button>(R.id.confirmButton).setOnClickListener {
            ClientManager.get().sendPhoneCode()
        }
        findViewById<Button>(R.id.confirmCodeButton).setOnClickListener {
            val code = findViewById<EditText>(R.id.authCode).text.toString()
            ClientManager.get().loginTg(code)
        }
        findViewById<Button>(R.id.chatListBtn).setOnClickListener {
            startActivity(Intent(this, ChatListActivity::class.java))
        }
    }
}