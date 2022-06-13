package globle.xhh.base

import android.app.Application
import com.h.android.HAndroid

/**
 *2022/6/12
 *@author zhangxiaohui
 *@describe
 */
class SApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        HAndroid.init(
            HAndroid.Builder(this)
                .setDebug(BuildConfig.DEBUG)
        )
    }
}