package domain.repository

import core.util.Resources
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import domain.model.PetItem
import domain.model.PetPhoto
import domain.model.result.CreatePetResult
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.Resource

interface PetDetailRepository {
    suspend fun fetchDoctors(): Flow<List<DoctorEntity>>
    suspend fun fetchBreeds(): Flow<List<BreedEntity>>
    suspend fun fetchColors(): Flow<List<ColorEntity>>
    suspend fun createPet(pet: PetItem): Flow<Resources<CreatePetResult>>
    suspend fun uploadProfilePicture(petId: String, file: ByteArray?): Flow<Resources<String>>
    suspend fun loadPetDoctor(petEntity: PetEntity): Flow<DoctorEntity>
    suspend fun loadPetColors(petEntity: PetEntity): Flow<List<ColorEntity>>
}