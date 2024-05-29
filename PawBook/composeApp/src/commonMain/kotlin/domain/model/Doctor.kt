package domain.model

import core.enums.PetType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Doctor(
    val address: Address,
    val skills: List<PetType>,
    val gps: Location,
    val name: String,
    val _id: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime
)