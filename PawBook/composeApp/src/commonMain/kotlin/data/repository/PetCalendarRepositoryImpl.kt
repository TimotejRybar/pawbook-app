package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.CalendarActivityEntity
import data.model.entity.PetEntity
import domain.repository.PetCalendarRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PetCalendarRepositoryImpl: PetCalendarRepository, KoinComponent {

    private val database: AppDatabase by inject()
    override suspend fun create(calendarActivity: CalendarActivityEntity): Flow<Resources<Boolean>> = flow {
        emit(Resources.Loading(true))
        try {
            val fetchResult = database.getCalendarDao().insert(calendarActivity)
            emit(Resources.Success(true))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }

    override suspend fun fetchForMonth(month: LocalDate): Flow<Resources<List<CalendarActivityEntity>>> = flow {
        val startOfMonth = month.atStartOfDayIn(TimeZone.UTC).toLocalDateTime(TimeZone.UTC)
        val endOfMonth = month.plus(1, DateTimeUnit.MONTH).atStartOfDayIn(TimeZone.UTC).toLocalDateTime(TimeZone.UTC)

        database.getCalendarDao().getForMonth(startOfMonth, endOfMonth)
    }

    override fun fetchPets(): Flow<List<PetEntity>> {
        return database.getPetDao().getAllAsFlow()
    }

    override suspend fun fetch(): Flow<List<CalendarActivityEntity>> {
        return database.getCalendarDao().getAllAsFlow()
    }
}