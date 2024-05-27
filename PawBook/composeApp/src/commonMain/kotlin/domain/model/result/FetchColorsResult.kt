package domain.model.result

import domain.model.PetColor

data class FetchColorsResult(
    val colors: List<PetColor>
)