package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.remote.PetApi
import domain.model.PetItem
import domain.model.result.CreatePetResult
import domain.repository.PetDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetDetailRepositoryImpl: PetDetailRepository, KoinComponent {

    private val petApi: PetApi by inject()
    private val database: AppDatabase by inject()

    override suspend fun fetchBreeds(): Flow<List<BreedEntity>> {
        return database.getBreedDao().getAllAsFlow()
    }

    override suspend fun fetchDoctors(): Flow<List<DoctorEntity>> {
        return database.getDoctorDao().getAllAsFlow()
    }

    override suspend fun fetchColors(): Flow<List<ColorEntity>> {
        return database.getColorDao().getAllAsFlow()
    }

    override suspend fun createPet(pet: PetItem): Flow<Resources<CreatePetResult>> = flow {
        emit(Resources.Loading(true))
        try {
            val result = petApi.create(pet)
            emit(Resources.Success(result))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
        }
    }

}