package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PetBreed (val id: String, val name: String, val key: String, val petType: String)