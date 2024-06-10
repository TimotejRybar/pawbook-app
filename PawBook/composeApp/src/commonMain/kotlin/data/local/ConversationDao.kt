package data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import data.model.entity.ColorEntity
import data.model.entity.ConversationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ConversationEntity>)

    @Query("SELECT * FROM ConversationEntity")
    fun getAllAsFlow(): Flow<List<ConversationEntity>>
}