package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PetColor (val petType: String,
                     val color: String, // HEX color
                     val _id: String,
                     val name: String,
                     val key: String,
                     val updatedAt: LocalDateTime,
                     val createdAt: LocalDateTime
)