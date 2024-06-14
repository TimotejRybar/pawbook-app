package domain.repository

import core.util.Resources
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import domain.model.Pet
import domain.model.WeightRecord
import domain.model.result.CreatePetResult
import domain.model.result.UpdatePetResult
import kotlinx.coroutines.flow.Flow

interface PetDetailRepository {
    suspend fun fetchDoctors(): Flow<List<DoctorEntity>>
    suspend fun fetchBreeds(): Flow<List<BreedEntity>>
    suspend fun fetchColors(): Flow<List<ColorEntity>>
    suspend fun createPet(pet: Pet): Flow<Resources<CreatePetResult>>
    suspend fun uploadProfilePicture(petId: String, file: ByteArray?): Flow<Resources<String>>
    suspend fun addWeightRecord(petId: String, weightRecord: WeightRecord): Flow<Resources<WeightRecord>>
    suspend fun loadPetDoctor(petEntity: PetEntity): Flow<DoctorEntity>
    suspend fun loadPetColors(petEntity: PetEntity): Flow<List<ColorEntity>>
    suspend fun loadPet(petId: String): Flow<PetEntity>
    suspend fun updatePet(pet: PetEntity?, petData: Pet): Flow<Resources<UpdatePetResult>>
}