package domain.repository

import core.util.Resources
import data.model.entity.PetPhotoEntity
import kotlinx.coroutines.flow.Flow
import presentation.screen.gallery.FileData

interface GalleryRepository {
    suspend fun create(petPhoto: PetPhotoEntity): Flow<Resources<Boolean>>
    suspend fun fetch(): Flow<List<PetPhotoEntity>>
    suspend fun uploadFiles(filesData: ArrayList<FileData>): Flow<Resources<Boolean>>
}