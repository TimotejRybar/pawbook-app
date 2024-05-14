package presentation
import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import appModule
import core.di.initKoin

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin(appDeclaration = {
            modules(appModule())
        })

        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}