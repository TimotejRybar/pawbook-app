package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.PetPhotoEntity
import domain.model.PetPhoto
import kotlinx.coroutines.flow.Flow

@Dao
interface PetPhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PetPhotoEntity>)

    @Query("SELECT * FROM PetPhotoEntity")
    fun getAllAsFlow(): Flow<List<PetPhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(petPhoto: PetPhotoEntity)
}