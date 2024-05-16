package core.util.stringByKey

interface ResourceProvider {
    fun getString(key: String): String
}
