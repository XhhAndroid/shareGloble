package globle.xhh.sharefun

import globle.xhh.base.BuildConfig
import globle.xhh.base.Config
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 *2022/6/13
 *@author zhangxiaohui
 *@describe
 */
class UpdateHandler : Client.ResultHandler {
    var client: Client? = null
    private val authorizationLock: Lock = ReentrantLock()
    private val gotAuthorization = authorizationLock.newCondition()
    private var authorizationState: TdApi.AuthorizationState? = null

    @Volatile
    private var haveAuthorization = false

    @Volatile
    private var needQuit = false

    @Volatile
    private var canQuit = false

    override fun onResult(obj: TdApi.Object) {
        when (obj.constructor) {
            TdApi.UpdateAuthorizationState.CONSTRUCTOR -> {
                onAuthorizationStateUpdated((obj as TdApi.UpdateAuthorizationState).authorizationState)
            }
            TdApi.UpdateUser.CONSTRUCTOR -> {

            }
            TdApi.UpdateUserStatus.CONSTRUCTOR -> {

            }
            TdApi.UpdateBasicGroup.CONSTRUCTOR -> {

            }
            TdApi.UpdateSupergroup.CONSTRUCTOR -> {

            }
            TdApi.UpdateNewChat.CONSTRUCTOR -> {

            }
            TdApi.UpdateChatLastMessage.CONSTRUCTOR -> {}
            else -> {}
        }
    }

    fun onAuthorizationStateUpdated(state: TdApi.AuthorizationState) {
        if(authorizationState != null){
            authorizationState = state
        }
        when (state.constructor) {
            TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> {
                val parameters = TdApi.TdlibParameters()
                parameters.databaseDirectory = Config.db_path()
                parameters.useMessageDatabase = true
                parameters.useSecretChats = true
                parameters.apiId = Config.app_id
                parameters.apiHash = Config.app_hash
                parameters.systemLanguageCode = "en"
                parameters.deviceModel = "Android"
                parameters.applicationVersion = BuildConfig.VERSION_NAME
                parameters.enableStorageOptimizer = true

                client!!.send(
                    TdApi.SetTdlibParameters(parameters),
                    AuthorizationRequestHandler()
                )
            }
            TdApi.AuthorizationStateWaitEncryptionKey.CONSTRUCTOR -> {
                client!!.send(TdApi.CheckDatabaseEncryptionKey(), AuthorizationRequestHandler());
            }
            TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> {
                //
            }
            TdApi.AuthorizationStateWaitOtherDeviceConfirmation.CONSTRUCTOR -> {
                val link = (state as TdApi.AuthorizationStateWaitOtherDeviceConfirmation).link;
                System.out.println("Please confirm this login link on another device: " + link);
            }
            TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> {
                val code = promptString("Please enter authentication code: ");
                client!!.send(TdApi.CheckAuthenticationCode(code), AuthorizationRequestHandler())
            }
            TdApi.AuthorizationStateWaitRegistration.CONSTRUCTOR -> {
                val firstName = promptString("Please enter your first name: ");
                val lastName = promptString("Please enter your last name: ");
                client!!.send(TdApi.RegisterUser(firstName, lastName), AuthorizationRequestHandler());
            }
            TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> {
                val password = promptString("Please enter password: ");
                client!!.send(TdApi.CheckAuthenticationPassword(password), AuthorizationRequestHandler());
            }
            TdApi.AuthorizationStateReady.CONSTRUCTOR -> {
                haveAuthorization = true;
                authorizationLock.lock();
                try {
                    gotAuthorization.signal();
                } finally {
                    authorizationLock.unlock();
                }
            }
            TdApi.AuthorizationStateLoggingOut.CONSTRUCTOR -> {
                haveAuthorization = false;
                print("Logging out");
            }
            TdApi.AuthorizationStateClosing.CONSTRUCTOR -> {
                haveAuthorization = false;
                print("Closing");
            }
            TdApi.AuthorizationStateClosed.CONSTRUCTOR -> {
                print("Closed");
                if (!needQuit) {
                    client = Client.create(UpdateHandler(), null, null); // recreate client after previous has closed
                } else {
                    canQuit = true;
                }
            }
            else -> {
                System.err.println("Unsupported authorization state:" + state);
            }
        }
    }

    private fun promptString(prompt: String): String? {
        print(prompt)
        val reader = BufferedReader(InputStreamReader(System.`in`))
        var str: String? = ""
        try {
            str = reader.readLine()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return str
    }

    class AuthorizationRequestHandler : Client.ResultHandler {
        override fun onResult(`object`: TdApi.Object) {
            when (`object`.constructor) {
                TdApi.Error.CONSTRUCTOR -> {
                    System.err.println("Receive an error:" + `object`)
//                    authorizationState = null
                }
                TdApi.Ok.CONSTRUCTOR -> {}
                else -> System.err.println("Receive wrong response from TDLib:" + `object`)
            }
        }
    }

}