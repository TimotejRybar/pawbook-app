package domain.model

import core.enums.PetType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PetItem(
    var _id: String?,
    var name: String,
    var shortDescription: String?,
    var petType: PetType,
    var owner: String?,
    val birthday: LocalDateTime?,
    val weight: Float,
    val color: List<String>,
    val breed: String, // key
    val photo: String,
    val updatedAt: LocalDateTime?,
    val createdAt: LocalDateTime?
) {
    companion object {
        fun empty(): PetItem {
            val item = PetItem(
                null,
                "",
                "",
                PetType.Dog,
                null,
                null,
                0f,
                arrayListOf(),
                "",
                "",
                null,
                null
            )
            return item
        }
    }
}