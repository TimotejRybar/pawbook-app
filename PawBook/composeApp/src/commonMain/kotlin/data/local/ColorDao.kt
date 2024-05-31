package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.ColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ColorEntity>)

    @Query("SELECT * FROM ColorEntity")
    fun getAllAsFlow(): Flow<List<ColorEntity>>

    @Query("SELECT * FROM ColorEntity WHERE color IN (:hexColors)")
    fun getFromHexStrings(hexColors: List<String>): Flow<List<ColorEntity>>
}