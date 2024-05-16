package domain

import core.util.stringByKey.ResourceProvider
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.StringResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.breed

interface Selectable {
    @OptIn(InternalResourceApi::class, ExperimentalResourceApi::class)
    fun localizedName(): String {
        return resourceProvider.getString(key)
    }

    val id: String
    val name: String
    val key: String
    val resourceProvider: ResourceProvider
}