package domain.model

import domain.model.enums.MediaType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PetPhoto(val _id: String,
                    val author: String?,
                    val pets: ArrayList<String>,
                    val name: String,
                    val mediaType: MediaType,
                    val description: String,
                    val file: String,
                    val updated: LocalDateTime?,
                    val created: LocalDateTime?)