package core.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import org.koin.core.context.GlobalContext

actual class IntentLauncher() {
    actual fun openMap(location: String) {
        val context: Context = GlobalContext.get().get()
        val gmmIntentUri = Uri.parse("geo:0,0?q=$location")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        }
    }
    actual fun callPhone(phoneNumber: String) {
        val context: Context = GlobalContext.get().get()
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}

actual fun getIntentLauncher(): IntentLauncher {
    return IntentLauncher()
}