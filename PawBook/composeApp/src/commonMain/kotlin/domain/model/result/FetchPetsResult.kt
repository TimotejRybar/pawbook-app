package domain.model.result

import domain.model.PetItem
import kotlinx.serialization.Serializable


@Serializable
data class FetchPetsResult(
    val pets: List<PetItem>,
)
