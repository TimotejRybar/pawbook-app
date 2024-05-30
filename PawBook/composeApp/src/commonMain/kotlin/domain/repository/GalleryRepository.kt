package domain.repository

import core.util.Resources
import data.model.entity.CalendarActivityEntity
import data.model.entity.PetEntity
import data.model.entity.PetPhotoEntity
import domain.model.PetPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface GalleryRepository {
    suspend fun create(petPhoto: PetPhotoEntity): Flow<Resources<Boolean>>
    suspend fun fetch(): Flow<List<PetPhotoEntity>>
}