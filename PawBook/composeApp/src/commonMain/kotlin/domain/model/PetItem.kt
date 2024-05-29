package domain.model

import core.enums.PetType
import kotlinx.serialization.Serializable

@Serializable
data class PetItem(
    var id: String?,
    var name: String,
    var shortDescription: String,
    var petType: PetType,
    var owner: String?,
    val birthday: String,
    val weight: Float,
    val color: List<String>,
    val breed: String, // key
    val photo: PetPhoto?,
    val gallery: List<PetPhoto>
) {
    companion object {
        fun empty(): PetItem {
            val item = PetItem(
                null,
                "",
                "",
                PetType.Dog,
                null,
                "",
                0f,
                arrayListOf(),
                "",
                PetPhoto(null, null, null),
                arrayListOf()
            )
            return item
        }
    }
}