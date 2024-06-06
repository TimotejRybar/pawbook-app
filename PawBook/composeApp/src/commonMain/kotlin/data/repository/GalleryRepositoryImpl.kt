package data.repository

import core.util.Resources
import data.local.AppDatabase
import data.model.entity.PetPhotoEntity
import domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GalleryRepositoryImpl: GalleryRepository, KoinComponent {

    private val database: AppDatabase by inject()
    override suspend fun create(petPhoto: PetPhotoEntity): Flow<Resources<Boolean>> = flow {
        emit(Resources.Loading(true))
        try {
            // TODO
            emit(Resources.Success(true))
        } catch (e: NetworkException) {
            emit(Resources.Error("no_internet"))
        } catch (e: Exception) {
            emit(Resources.Error("internal_error"))
            e.printStackTrace()
        }
    }

    override suspend fun fetch(): Flow<List<PetPhotoEntity>> {
        return database.getPetPhotoDao().getAllAsFlow()
    }
}