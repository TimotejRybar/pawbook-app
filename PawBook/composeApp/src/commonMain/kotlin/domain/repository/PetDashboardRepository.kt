package domain.repository

import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import domain.model.WeightRecord
import kotlinx.coroutines.flow.Flow

interface PetDashboardRepository {
    suspend fun loadColors(hexColors: List<String>): Flow<List<ColorEntity>>
    suspend fun loadDoctor(doctorId: String): Flow<DoctorEntity>
    suspend fun loadPet(petId: String): Flow<PetEntity>
    suspend fun addWeightRecord(petId: String, weight: Float): Flow<WeightRecord>
}