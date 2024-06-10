package domain.model.result

import domain.model.Pet
import kotlinx.serialization.Serializable

@Serializable
data class CreatePetResult (
    val pet: Pet,
    val message: String
)