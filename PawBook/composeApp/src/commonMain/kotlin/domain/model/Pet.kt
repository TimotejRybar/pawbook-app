package domain.model

import core.enums.Gender
import core.enums.PetType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Pet(
    var _id: String?,
    var name: String,
    var shortDescription: String?,
    var petType: PetType,
    var owner: String?,
    val birthday: LocalDateTime?,
    val gender: String,
    val weight: Float,
    val color: List<String>,
    val breed: String, // key
    val doctor: String?, // ID
    val photo: String?,
    val trackWeight: Boolean,
    val weightHistory: ArrayList<WeightRecord>,
    val trackEpilepsy: Boolean,
    val epilepsyHistory: ArrayList<EpilepsyRecord>,
    val updatedAt: LocalDateTime?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun empty(): Pet {
            val item = Pet(
                null,
                "",
                "",
                PetType.Dog,
                null,
                null,
                Gender.BOY.value,
                0f,
                arrayListOf(),
                "",
                null,
                null,
                true,
                arrayListOf(),
                true,
                arrayListOf(),
                null,
                null
            )
            return item
        }
    }
}