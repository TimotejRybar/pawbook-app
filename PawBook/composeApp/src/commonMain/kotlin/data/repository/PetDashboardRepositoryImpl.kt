package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import data.remote.PetApi
import domain.model.EpilepsyRecord
import domain.model.WeightRecord
import domain.repository.PetDashboardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PetDashboardRepositoryImpl: PetDashboardRepository, KoinComponent {

    private val database: AppDatabase by inject()
    private val petApi: PetApi by inject()

    override suspend fun loadColors(hexColors: List<String>): Flow<List<ColorEntity>> {
       return database.getColorDao().getFromHexStrings(hexColors);
    }

    override suspend fun loadPet(petId: String): Flow<PetEntity> {
        return database.getPetDao().getById(petId)
    }

    override suspend fun addWeightRecord(petId: String, weight: WeightRecord): Flow<Resources<WeightRecord>> = flow {
        try {
            val saved = petApi.addWeightRecord(petId, weight)
            emit(Resources.Success(saved))

            database.getPetDao().getById(petId).collect {
                it.weightHistory.add(saved)
                database.getPetDao().update(it)
            }
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override suspend fun addEpilepsyRecord(petId: String, epilepsyRecord: EpilepsyRecord): Flow<Resources<EpilepsyRecord>> = flow {
        try {
            val saved = petApi.addEpilepsyRecord(petId, epilepsyRecord)
            emit(Resources.Success(saved))

            database.getPetDao().getById(petId).collect {
                it.epilepsyHistory.add(saved)
                database.getPetDao().update(it)
            }
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

    override suspend fun loadDoctor(doctorId: String): Flow<DoctorEntity> {
        return database.getDoctorDao().getById(doctorId);
    }

    override suspend fun loadPetProfilePhoto(petId: String): Flow<Resources<ByteArray>> = flow {
        emit(Resources.Loading(true))
        try {
            val result = petApi.getProfilePhoto(petId)
            emit((Resources.Success(result?.body())))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }
}