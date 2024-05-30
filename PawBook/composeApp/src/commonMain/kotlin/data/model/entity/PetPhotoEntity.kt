package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
data class PetPhotoEntity(
    @PrimaryKey val id: String,
    val author: String,
    val pets: List<String>,
    val description: String?,
    val file: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)