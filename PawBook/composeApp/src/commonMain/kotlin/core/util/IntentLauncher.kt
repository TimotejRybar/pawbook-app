package core.util

expect class IntentLauncher {
    fun openMap(location: String)
    fun callPhone(phoneNumber: String)
}

expect fun getIntentLauncher(): IntentLauncher