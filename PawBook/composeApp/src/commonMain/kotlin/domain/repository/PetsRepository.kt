package domain.repository

import core.util.Resources
import data.model.entity.PetEntity
import domain.model.CalendarActivity
import domain.model.Pet
import kotlinx.coroutines.flow.Flow

interface PetsRepository {
    suspend fun create(pet: Pet): Flow<Resources<Pet>>
    suspend fun fetch(): Flow<List<PetEntity>>
    suspend fun createCalendarActivity(calendarActivity: CalendarActivity): Flow<Resources<CalendarActivity>>
    suspend fun fetchPetProfilePhoto(petId: String): Flow<Resources<ByteArray>>
}