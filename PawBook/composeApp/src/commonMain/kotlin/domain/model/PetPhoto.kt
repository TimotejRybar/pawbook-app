package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PetPhoto(val _id: String,
                    val author: String?,
                    val pets: List<String>,
                    val description: String,
                    val file: String,
                    val updated: LocalDateTime?,
                    val created: LocalDateTime?)