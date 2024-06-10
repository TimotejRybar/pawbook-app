package domain.repository

import data.model.entity.ConversationEntity
import kotlinx.coroutines.flow.Flow

interface ConversationsRepository {
    suspend fun fetch(): Flow<List<ConversationEntity>>
}