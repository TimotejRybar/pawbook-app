package domain.model.result

import domain.model.PetItem
import kotlinx.serialization.Serializable

@Serializable
data class CreatePetResult (
    val pet: PetItem,
    val message: String
)