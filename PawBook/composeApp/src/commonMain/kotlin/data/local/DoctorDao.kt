package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import data.model.entity.DoctorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Insert
    suspend fun insertAll(items: List<DoctorEntity>)

    @Query("SELECT * FROM DoctorEntity")
    fun getAllAsFlow(): Flow<List<DoctorEntity>>
}