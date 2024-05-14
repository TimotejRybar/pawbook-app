package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PetItem(var _id: String, var name: String, var shortDescription: String)