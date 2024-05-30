package data.repository

import data.model.entity.DoctorEntity
import domain.repository.PetDashboardRepository
import data.local.AppDatabase
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDashboardRepositoryImpl: PetDashboardRepository, KoinComponent {

    private val database: AppDatabase by inject()

    override suspend fun fetchDoctors(): Flow<List<DoctorEntity>> {

    }
}