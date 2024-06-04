package domain.model.result

import domain.model.StorageEntry
import kotlinx.serialization.Serializable

@Serializable
data class CreateStorageEntryResult(val message: String, val storageEntry: StorageEntry)