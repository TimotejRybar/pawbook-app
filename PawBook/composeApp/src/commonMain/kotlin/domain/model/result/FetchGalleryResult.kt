package domain.model.result

import domain.model.Doctor
import domain.model.PetPhoto
import kotlinx.serialization.Serializable

@Serializable
data class FetchGalleryResult (val gallery: List<PetPhoto>)