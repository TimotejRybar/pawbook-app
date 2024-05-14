package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PetBreed (val _id: String, val name: String, val key: String)