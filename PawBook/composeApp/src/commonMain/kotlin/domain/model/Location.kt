package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val latitude: Float,
    val longitude: Float
)