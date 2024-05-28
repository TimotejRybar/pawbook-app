package domain.repository

import core.util.Resources
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import domain.model.PetItem
import domain.model.result.CreatePetResult
import kotlinx.coroutines.flow.Flow

interface PetDetailRepository {
    suspend fun fetchDoctors(): Flow<List<DoctorEntity>>
    suspend fun fetchBreeds(): Flow<List<BreedEntity>>
    suspend fun fetchColors(): Flow<List<ColorEntity>>
    suspend fun createPet(pet: PetItem): Flow<Resources<CreatePetResult>>
}