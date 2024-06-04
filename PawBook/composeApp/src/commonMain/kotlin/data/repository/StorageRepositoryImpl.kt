package data.repository

import data.local.AppDatabase
import data.model.entity.StorageEntryEntity
import domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StorageRepositoryImpl : StorageRepository, KoinComponent {

    private val database: AppDatabase by inject()

    override suspend fun fetch(): Flow<List<StorageEntryEntity>> {
        return database.getStorageDao().getAllAsFlow()
    }
}