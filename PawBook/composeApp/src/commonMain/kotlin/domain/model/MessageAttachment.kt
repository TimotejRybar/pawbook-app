package domain.model

import domain.model.enums.AttachmentType
import kotlinx.serialization.Serializable

@Serializable
data class MessageAttachment(
    val data: String,
    val attachmentType: AttachmentType
)