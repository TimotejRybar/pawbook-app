package domain.model

import domain.model.enums.PetType
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
    val photo: String,
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
                ""
            )
            return item
        }
    }
}