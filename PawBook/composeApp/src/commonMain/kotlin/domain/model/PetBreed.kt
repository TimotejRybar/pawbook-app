package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PetBreed (val petType: String,
                     override val id: String,
                     override val name: String,
                     override val key: String,
                     override val updated: Long,
                     override val created: Long
): Selectable