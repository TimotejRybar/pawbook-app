package domain.model

import domain.model.enums.StorageEntryType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class StorageEntry(
    val _id: String?,
    val storageEntryType: StorageEntryType, val name: String, val vPath: String, val storageKey: String, val createdAt: LocalDateTime?, var updatedAt: LocalDateTime?
)
