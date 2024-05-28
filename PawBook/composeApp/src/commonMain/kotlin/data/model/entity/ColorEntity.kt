package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity
data class ColorEntity(
    @PrimaryKey val id: String,
    val petType: String,
    val name: String,
    val key: String,
    val color: String, // HEX color
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)