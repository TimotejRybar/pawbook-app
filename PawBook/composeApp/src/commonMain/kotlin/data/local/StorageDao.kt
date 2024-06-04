package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.StorageEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<StorageEntryEntity>)

    @Query("SELECT * FROM StorageEntryEntity")
    fun getAllAsFlow(): Flow<List<StorageEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storageEntry: StorageEntryEntity)
}