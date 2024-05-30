package domain.repository

import core.util.Resources
import data.model.entity.CalendarActivityEntity
import data.model.entity.PetEntity
import domain.model.CalendarActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface PetCalendarRepository {
    suspend fun create(calendarActivity: CalendarActivityEntity): Flow<Resources<Boolean>>
    suspend fun fetch(): Flow<List<CalendarActivityEntity>>

    suspend fun fetchForMonth(date: LocalDate): Flow<Resources<List<CalendarActivityEntity>>>
    fun fetchPets(): Flow<List<PetEntity>>

}