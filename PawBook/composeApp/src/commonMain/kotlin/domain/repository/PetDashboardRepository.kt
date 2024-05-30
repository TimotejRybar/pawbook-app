package domain.repository

import data.model.entity.DoctorEntity
import kotlinx.coroutines.flow.Flow

interface PetDashboardRepository {
    suspend fun fetchDoctors(): Flow<List<DoctorEntity>>
}