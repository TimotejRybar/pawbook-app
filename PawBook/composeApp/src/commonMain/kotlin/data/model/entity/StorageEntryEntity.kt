package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.enums.StorageEntryType
import kotlinx.datetime.LocalDateTime

@Entity
data class StorageEntryEntity(
    @PrimaryKey val id: String,
    val storageEntryType: StorageEntryType,
    val name: String,
    val vPath: String,
    val storageKey: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
