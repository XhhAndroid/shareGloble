package globle.xhh.share

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import globle.xhh.base.Config
import globle.xhh.sharefun.ClientManager
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import java.util.*

class MainActivity : AppCompatActivity() {
    private val clientManager by lazy {
        ClientManager()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.confirmButton).setOnClickListener {
            clientManager.init()
//            Initialization parameters are needed: call setTdlibParameters first
        }
    }

    fun setTdlibParameters() {
//        Client.execute(TdApi.SetLogVerbosityLevel(5))
//
//        mClient!!.send(TdApi.SetTdlibParameters(
//            TdApi.TdlibParameters(
//                BuildConfig.DEBUG, Config.db_path(), Config.file_path(), true, true, true, true,
//                Config.app_id, Config.app_hash, Locale.CHINESE.language, "android", null,
//                BuildConfig.VERSION_NAME, true, false
//            )
//        ), Client.ResultHandler {
//                Log.d("", "SetTdlibParameters${it.toString()}")
//            })
    }



}