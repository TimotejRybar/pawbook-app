package domain.model.request

import core.enums.PetType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
class UpdatePetRequest (
    val _id: String,
    var name: String,
    var shortDescription: String?,
    var petType: PetType,
    var owner: String?,
    val birthday: LocalDateTime?,
    val gender: String,
    var weight: Float,
    val color: List<String>,
    var breed: String, // key
    val doctor: String?, // ID
    val photo: String?,
    val trackWeight: Boolean,
    val trackEpilepsy: Boolean,
    )