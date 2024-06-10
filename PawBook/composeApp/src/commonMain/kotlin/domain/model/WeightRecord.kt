package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class WeightRecord (
    val _id: String,
    val weight: Float,
    val created: LocalDateTime
)