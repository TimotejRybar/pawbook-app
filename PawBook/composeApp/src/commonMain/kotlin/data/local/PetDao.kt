package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.model.entity.PetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PetEntity>)

    @Query("SELECT * FROM PetEntity")
    fun getAllAsFlow(): Flow<List<PetEntity>>

    @Query("SELECT * FROM PetEntity WHERE id=:petId")
    fun getById(petId: String): Flow<PetEntity>

    @Update
    fun update(pet: PetEntity)
}