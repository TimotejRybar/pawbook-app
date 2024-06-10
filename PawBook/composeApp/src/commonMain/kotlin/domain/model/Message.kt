package domain.model

import domain.model.result.User
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    var id: String,
    var sender: User,
    var message: String,
    var attachment: List<MessageAttachment>,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)