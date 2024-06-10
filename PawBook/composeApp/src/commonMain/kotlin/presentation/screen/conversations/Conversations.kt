package presentation.screen.conversations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.model.entity.ConversationEntity
import domain.model.Conversation
import org.koin.compose.koinInject

@Composable
fun Conversations(viewModel: ConversationsViewModel = koinInject()) {

    val conversations by viewModel.conversations.collectAsState()

    LaunchedEffect(true) {
        viewModel.fetch()
    }

    ConversationsScreen(conversations){

    }
}

@Composable
fun ConversationsScreen(
    conversations: List<ConversationEntity>,
    onConversationClick: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        conversations.forEach { conversation ->
            ConversationItem(conversation = conversation, onClick = { onConversationClick(conversation.id) })
        }
    }
}

@Composable
fun ConversationItem(conversation: ConversationEntity, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = conversation.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = conversation.messages.last().message, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
        }
    }
}
