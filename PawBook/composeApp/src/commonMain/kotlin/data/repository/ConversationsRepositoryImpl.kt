package data.repository

import data.local.AppDatabase
import data.model.entity.ConversationEntity
import domain.repository.ConversationsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConversationsRepositoryImpl: ConversationsRepository, KoinComponent {

    private val database: AppDatabase by inject()
    override suspend fun fetch(): Flow<List<ConversationEntity>> {
      return flow {} // return database.getConversationDao().getAllAsFlow()
    }
}