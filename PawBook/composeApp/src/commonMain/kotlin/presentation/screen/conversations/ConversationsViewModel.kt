package presentation.screen.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.entity.ConversationEntity
import data.repository.ConversationsRepositoryImpl
import domain.model.Conversation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ConversationsViewModel: ViewModel(), KoinComponent {

    private val conversationsRepository: ConversationsRepositoryImpl by inject()
    private val _conversations = MutableStateFlow<ArrayList<ConversationEntity>>(arrayListOf())
    val conversations: StateFlow<ArrayList<ConversationEntity>> = _conversations

    fun fetch() {
        viewModelScope.launch {
            conversationsRepository.fetch().collect {
                _conversations.value.clear()
                _conversations.value.addAll(it)
            }
        }
    }
}