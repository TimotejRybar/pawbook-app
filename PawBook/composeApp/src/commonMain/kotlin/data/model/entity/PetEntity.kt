package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import core.enums.PetType
import kotlinx.datetime.LocalDateTime
import presentation.components.calendar.CalendarUiState

@Entity
data class PetEntity(
    @PrimaryKey val id: String,
    val petType: PetType,
    val name: String,
    val shortDescription: String?,
    val birthDay: LocalDateTime?,
    val weight: Float,
    val color: List<String>,
    val breed: String,
    val photo: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)