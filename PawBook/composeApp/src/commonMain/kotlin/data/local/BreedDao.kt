package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<BreedEntity>)

    @Query("SELECT * FROM BreedEntity")
    fun getAllAsFlow(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM BreedEntity WHERE id=:breedId")
    fun getById(breedId: String): Flow<BreedEntity>
}