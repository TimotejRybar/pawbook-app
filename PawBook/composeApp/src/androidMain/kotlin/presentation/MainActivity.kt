package presentation
import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import appModule
import core.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initKoin(appDeclaration = {
            modules(appModule())
            androidContext(this@MainActivity)
        })

        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

