package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.model.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Insert
    suspend fun insertAll(items: List<BreedEntity>)

    @Query("SELECT * FROM BreedEntity")
    fun getAllAsFlow(): Flow<List<BreedEntity>>
}