package domain.model.result

import domain.model.PetColor
import kotlinx.serialization.Serializable

@Serializable
data class FetchColorsResult(
    val colors: List<PetColor>
)