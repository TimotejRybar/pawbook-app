package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PetPhoto(val file: String?, val created: LocalDateTime?, val updated: LocalDateTime?)