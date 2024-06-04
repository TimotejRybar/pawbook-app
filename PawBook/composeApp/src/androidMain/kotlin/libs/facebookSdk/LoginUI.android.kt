/*package libs.facebookSdk

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.compose.runtime.Composable
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.core.context.GlobalContext
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.login
import presentation.screen.login.StyledButton

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun LoginButton() {
    val context: Context = GlobalContext.get().get()
    StyledButton(text = stringResource(Res.string.login)) {
        login(context)
    }
}

fun login(context: Context) {
    if (context is ActivityResultRegistryOwner) {
        val callbackManager = CallbackManager.Factory.create()
        val loginManager = LoginManager.getInstance()
        loginManager.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onCancel() {
                    Toast.makeText(context, "Login canceled!", Toast.LENGTH_LONG).show()
                }

                override fun onError(error: FacebookException) {
                    Log.e("Login", error.message ?: "Unknown error")
                    Toast.makeText(context, "Login failed with errors!", Toast.LENGTH_LONG).show()
                }

                override fun onSuccess(result: LoginResult) {
                    Toast.makeText(context, "Login succeed!", Toast.LENGTH_LONG).show()
                }
            })
    }
}*/