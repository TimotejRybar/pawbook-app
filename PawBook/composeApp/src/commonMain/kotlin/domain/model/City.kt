package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class City (
    val _id: String?,
    val name: String,
    val key: String,
    val country: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime
)