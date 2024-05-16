package core.util.stringByKey

import android.content.Context

class AndroidResourceProvider(private val context: Context) : ResourceProvider {
    override fun getString(key: String): String {
        val resId = context.resources.getIdentifier(key, "string", context.packageName)
        return if (resId != 0) {
            context.getString(resId)
        } else {
            key // Fallback to key if no resource ID is found
        }
    }
}