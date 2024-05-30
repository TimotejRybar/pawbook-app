package domain.model.result

import domain.model.PetPhoto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePetPhotoResult(val petPhoto: PetPhoto)