package domain.model.result

import domain.model.PetItem

data class CreatePetResult (
    val pet: PetItem,
    val message: String
)