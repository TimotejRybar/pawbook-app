package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.Message
import domain.model.result.User
import kotlinx.datetime.LocalDateTime

@Entity
data class ConversationEntity(
    @PrimaryKey val id: String,
    val name: String,
    var members: List<User>,
    var messages: List<Message>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
