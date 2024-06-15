package domain.repository

import core.util.Resources
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import domain.model.EpilepsyRecord
import domain.model.WeightRecord
import kotlinx.coroutines.flow.Flow

interface PetDashboardRepository {
    suspend fun loadColors(hexColors: List<String>): Flow<List<ColorEntity>>
    suspend fun loadDoctor(doctorId: String): Flow<DoctorEntity>
    suspend fun loadPet(petId: String): Flow<PetEntity>
    suspend fun addWeightRecord(petId: String, weight: WeightRecord): Flow<Resources<WeightRecord>>
    suspend fun addEpilepsyRecord(petId: String, epilepsyRecord: EpilepsyRecord): Flow<Resources<EpilepsyRecord>>
    suspend fun loadPetProfilePhoto(petId: String): Flow<Resources<ByteArray>>
}