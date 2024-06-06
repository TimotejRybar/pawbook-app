package domain.model.result

import domain.model.StorageEntry
import kotlinx.serialization.Serializable

@Serializable
data class UploadFileResult(
    val file: StorageEntry
)