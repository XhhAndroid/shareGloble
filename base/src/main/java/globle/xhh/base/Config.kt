package globle.xhh.base

import android.content.Context
import android.os.Environment
import com.h.android.HAndroid
import java.io.File

/**
 *2022/6/9
 *@author zhangxiaohui
 *@describe
 */
object Config {

    val app_id = 10522744
    val app_hash = "0d742e04e3ae841c1369db64771aaa14"

    private fun innerPathBase(): String {
        return HAndroid.getApplication().getDir("sApp", Context.MODE_PRIVATE).absolutePath
    }

    fun db_path(): String {
        return innerPathBase() + File.separator + "db"
    }

    fun file_path(): String {
        return innerPathBase() + File.separator + "file"
    }
}