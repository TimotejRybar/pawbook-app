package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.CalendarActivityEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDateTime

@Dao
interface CalendarActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CalendarActivityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CalendarActivityEntity>)

    @Query("SELECT * FROM CalendarActivityEntity")
    fun getAllAsFlow(): Flow<List<CalendarActivityEntity>>

    @Query("""
        SELECT * FROM CalendarActivityEntity 
        WHERE start >= :startOfMonth AND start < :endOfMonth
           OR "end" >= :startOfMonth AND "end" < :endOfMonth
           OR start < :startOfMonth AND "end" > :endOfMonth
    """)
    fun getForMonth(startOfMonth: LocalDateTime, endOfMonth: LocalDateTime): Flow<List<CalendarActivityEntity>>

}