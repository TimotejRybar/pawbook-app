package domain.model

import core.util.stringByKey.ResourceProvider
import domain.Selectable
import kotlinx.serialization.Serializable

@Serializable
data class PetBreed (val petType: String,
                     override val id: String,
                     override val name: String,
                     override val key: String, override val resourceProvider: ResourceProvider
): Selectable