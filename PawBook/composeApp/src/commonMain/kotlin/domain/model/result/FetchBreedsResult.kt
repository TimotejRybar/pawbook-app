package domain.model.result

import domain.model.PetBreed
import kotlinx.serialization.Serializable

@Serializable
data class  FetchBreedsResult(
    val breeds: List<PetBreed> = listOf()
)
