package globle.xhh.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.drinkless.td.libcore.telegram.Client
import globle.xhh.base.Config
import org.drinkless.td.libcore.telegram.TdApi
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTdlibParameters()
        findViewById<Button>(R.id.confirmButton).setOnClickListener {
            Client.create(Client.ResultHandler { },
                Client.ExceptionHandler { },
                Client.ExceptionHandler { }).send(TdApi.SendPhoneNumberVerificationCode(
                "+8618328717019", TdApi.PhoneNumberAuthenticationSettings(
                    false, false, false,
                    false, emptyArray()
                )
            ),
                Client.ResultHandler { result ->
                    Log.d("", "SetTdlibParameters${result.toString()}")
                }, Client.ExceptionHandler {
                    Log.d("", "")
                })
//            Initialization parameters are needed: call setTdlibParameters first
        }
    }

    fun setTdlibParameters() {
        Client.create(Client.ResultHandler { },
            Client.ExceptionHandler { },
            Client.ExceptionHandler { }).send(TdApi.SetTdlibParameters(
            TdApi.TdlibParameters(
                BuildConfig.DEBUG, Config.db_path(), Config.file_path(), true, true, true, true,
                Config.app_id, Config.app_hash, Locale.CHINESE.language, "android", null,
                BuildConfig.VERSION_NAME, true, false
            )
        ),
            Client.ResultHandler {
                Log.d("", "SetTdlibParameters${it.toString()}")
            })
    }
}