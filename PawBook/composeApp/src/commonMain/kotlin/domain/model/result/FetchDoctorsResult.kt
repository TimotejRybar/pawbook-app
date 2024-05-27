package domain.model.result

import domain.model.Doctor
import domain.model.PetBreed
import kotlinx.serialization.Serializable

@Serializable
data class FetchDoctorsResult(
    val doctors: List<Doctor> = listOf()
)
