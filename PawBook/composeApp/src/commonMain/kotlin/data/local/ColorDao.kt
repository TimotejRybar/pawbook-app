package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.model.entity.ColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Insert
    suspend fun insertAll(items: List<ColorEntity>)

    @Query("SELECT * FROM ColorEntity")
    fun getAllAsFlow(): Flow<List<ColorEntity>>
}