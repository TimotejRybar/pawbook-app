package domain.model

import domain.model.enums.PetType
import kotlinx.serialization.Serializable

@Serializable
data class PetItem(var id: String, var name: String, var shortDescription: String,var petType: PetType)