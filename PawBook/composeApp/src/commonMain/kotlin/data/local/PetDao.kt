package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.DoctorEntity
import domain.model.PetItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PetDao>)

    @Query("SELECT * FROM PetEntity")
    fun getAllAsFlow(): Flow<List<PetDao>>
}