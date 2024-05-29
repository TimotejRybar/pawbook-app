package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import core.enums.ActivityType
import core.enums.PetType
import domain.model.PetItem
import kotlinx.datetime.LocalDateTime

@Entity
data class CalendarActivityEntity(
    @PrimaryKey val id: String,
    val pet: PetItem,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val activityType: ActivityType,
    val location: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)