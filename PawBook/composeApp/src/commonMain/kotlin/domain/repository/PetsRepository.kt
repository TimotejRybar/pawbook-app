package domain.repository

import core.util.Resources
import data.model.entity.CalendarActivityEntity
import data.model.entity.PetEntity
import domain.model.CalendarActivity
import domain.model.PetItem
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    suspend fun create(pet: PetItem): Flow<Resources<PetItem>>
    suspend fun fetch(): Flow<List<PetEntity>>
    suspend fun createCalendarActivity(calendarActivity: CalendarActivity): Flow<Resources<CalendarActivity>>
}