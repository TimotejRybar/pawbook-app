package core.util.stringByKey

import androidx.compose.runtime.Composable
import core.util.stringByKey.data.BreedString.getBreedStringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource

enum class StringType {
    BREED
}

object StringByKey {
    @OptIn(ExperimentalResourceApi::class)
    fun getStringValue(stringType: StringType, key: String): StringResource {
        when(stringType) {
            StringType.BREED -> return getBreedStringResource(key);
        }
    }
}