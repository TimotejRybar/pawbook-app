package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.PetEntity
import data.remote.CalendarActivityApi
import data.remote.PetApi
import domain.model.CalendarActivity
import domain.model.PetItem
import domain.repository.PetsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetsRepoitoryImpl: PetsRepository, KoinComponent {

    private val petsApi: PetApi by inject()
    private val calendarApi: CalendarActivityApi by inject()
    private val database: AppDatabase by inject()

    override suspend fun create(pet: PetItem): Flow<Resources<PetItem>> = flow {
        emit(Resources.Loading(true))
        try {
            val fetchResult = petsApi.create(pet)
            emit(Resources.Success(fetchResult.pet))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }

    override suspend fun fetch(): Flow<List<PetEntity>> {
       return database.getPetDao().getAllAsFlow()
    }

    override suspend fun createCalendarActivity(calendarActivity: CalendarActivity): Flow<Resources<CalendarActivity>> = flow {
        emit(Resources.Loading(true))
        try {
            val result = calendarApi.createCalendarActivity(calendarActivity)
            emit(Resources.Success(result))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resources.Error("Error while creating CalendarActivity"))
        }

    }
}