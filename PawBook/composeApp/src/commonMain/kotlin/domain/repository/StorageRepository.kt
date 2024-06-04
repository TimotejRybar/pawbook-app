package domain.repository

import data.model.entity.StorageEntryEntity
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun fetch(): Flow<List<StorageEntryEntity>>
}