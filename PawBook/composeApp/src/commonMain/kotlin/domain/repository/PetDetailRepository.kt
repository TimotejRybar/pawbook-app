package domain.repository

import core.util.Resources
import domain.model.PetItem
import domain.model.enums.PetType
import domain.model.result.CreatePetResult
import domain.model.result.FetchBreedsResult
import domain.model.result.LoginResult
import kotlinx.coroutines.flow.Flow

interface PetDetailRepository {
    suspend fun fetchBreeds(petType: PetType): Flow<Resources<FetchBreedsResult>>
    suspend fun createPet(pet: PetItem): Flow<Resources<CreatePetResult>>
}