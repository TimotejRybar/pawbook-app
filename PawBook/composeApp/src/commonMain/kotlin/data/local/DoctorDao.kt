package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.DoctorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DoctorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<DoctorEntity>)

    @Query("SELECT * FROM DoctorEntity")
    fun getAllAsFlow(): Flow<List<DoctorEntity>>

    @Query("SELECT * FROM DoctorEntity WHERE id=:doctorId")
    fun getById(doctorId: String): Flow<DoctorEntity>
}