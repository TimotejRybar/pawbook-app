package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class StorageEntry(
    val _id: String?,
    val storageEntryType: String, val name: String, val vPath: String, val storageKey: String, val createdAt: LocalDateTime?, var updatedAt: LocalDateTime?
)