package sk.uplab.pawbook.core.util.stringByKey

import core.util.stringByKey.ResourceProvider
import platform.Foundation.NSBundle

class IOSResourceProvider : ResourceProvider {
    override fun getString(key: String): String {
        val bundle = NSBundle.mainBundle
        val localizedString = bundle.localizedStringForKey(key, key, null)
        return localizedString
    }
}