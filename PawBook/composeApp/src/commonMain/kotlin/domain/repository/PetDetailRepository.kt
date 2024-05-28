package domain.repository

import core.util.Resources
import domain.model.PetItem
import domain.model.enums.PetType
import domain.model.result.CreatePetResult
import domain.model.result.FetchBreedsResult
import domain.model.result.FetchColorsResult
import domain.model.result.FetchDoctorsResult
import domain.model.result.LoginResult
import kotlinx.coroutines.flow.Flow

interface PetDetailRepository {
    suspend fun fetchDoctors(): Flow<Resources<FetchDoctorsResult>>
    suspend fun fetchBreeds(): Flow<Resources<FetchBreedsResult>>
    suspend fun fetchColors(): Flow<Resources<FetchColorsResult>>

    suspend fun createPet(pet: PetItem): Flow<Resources<CreatePetResult>>
}