package core.util

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class IntentLauncher() {
    actual fun openMap(location: String) {
            UIApplication.sharedApplication.openURL(NSURL(location))
    }
    actual fun callPhone(phoneNumber: String) {
        val phoneUrl = "tel://$phoneNumber"
        val url = NSURL(phoneUrl)
        UIApplication.sharedApplication.openURL(url)
    }
}

actual fun getIntentLauncher(): IntentLauncher {
    return IntentLauncher()
}