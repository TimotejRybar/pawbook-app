package domain.model

import domain.model.enums.PetType
import kotlinx.serialization.Serializable

@Serializable
data class PetItem(
    var id: String,
    var name: String,
    var shortDescription: String,
    var petType: PetType,
    var owner: String,
    val birthday: String,
    val weight: Float,
    val color: String,
    val breed: String, // key
    val photo: String,
) {
    companion object {
        fun empty(): PetItem {
            val item = PetItem(
                "CREATE",
                "",
                "",
                PetType.Dog,
                "",
                "",
                0f,
                "",
                "",
                ""
            )
            return item
        }
    }
}