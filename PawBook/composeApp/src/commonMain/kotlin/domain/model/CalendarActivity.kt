package domain.model

import core.enums.ActivityType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class CalendarActivity(
    val _id: String,
    val pet: PetItem,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val activityType: ActivityType,
    val location: String,
    val description: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime
)