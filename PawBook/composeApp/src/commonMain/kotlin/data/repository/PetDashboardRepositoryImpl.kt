package data.repository

import data.local.AppDatabase
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import domain.model.WeightRecord
import domain.repository.PetDashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PetDashboardRepositoryImpl: PetDashboardRepository, KoinComponent {

    private val database: AppDatabase by inject()

    override suspend fun loadColors(hexColors: List<String>): Flow<List<ColorEntity>> {
       return database.getColorDao().getFromHexStrings(hexColors);
    }

    override suspend fun loadPet(petId: String): Flow<PetEntity> {
        return database.getPetDao().getById(petId)
    }

    override suspend fun addWeightRecord(petId: String, weight: Float): Flow<WeightRecord> = flow {
       database.getPetDao().getById(petId).collect {
            val weighRecord = WeightRecord(
                "",
                weight,
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            )
            it.weightHistory.add(weighRecord)
            database.getPetDao().update(it)
            emit(weighRecord)
        }
    }

    override suspend fun loadDoctor(doctorId: String): Flow<DoctorEntity> {
        return database.getDoctorDao().getById(doctorId);
    }
}