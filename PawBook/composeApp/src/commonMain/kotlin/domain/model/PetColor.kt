package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PetColor (val petType: String,
                     val color: String, // HEX color
                     override val id: String,
                     override val name: String,
                     override val key: String,
                     override val updated: Long,
                     override val created: Long
): Selectable