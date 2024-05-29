package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.CalendarActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CalendarActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CalendarActivityEntity>)

    @Query("SELECT * FROM CalendarActivityEntity")
    fun getAllAsFlow(): Flow<List<CalendarActivityEntity>>
}