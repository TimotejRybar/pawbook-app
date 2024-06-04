package domain.model.result

import domain.model.StorageEntry
import kotlinx.serialization.Serializable

@Serializable
data class FetchStorageResult(
    val storage: List<StorageEntry>,
)
