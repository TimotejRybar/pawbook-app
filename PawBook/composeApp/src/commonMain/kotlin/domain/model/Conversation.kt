package domain.model

import domain.model.result.User
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Conversation(
    var id: String,
    var name: String,
    var members: List<User>,
    var messages: List<Message>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)