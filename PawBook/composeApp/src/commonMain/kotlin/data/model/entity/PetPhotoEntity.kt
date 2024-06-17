package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.enums.MediaType
import kotlinx.datetime.LocalDateTime

@Entity
data class PetPhotoEntity(
    @PrimaryKey val id: String,
    val author: String,
    val pets: ArrayList<String>,
    val name: String,
    val mediaType: MediaType,
    val description: String?,
    val file: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)