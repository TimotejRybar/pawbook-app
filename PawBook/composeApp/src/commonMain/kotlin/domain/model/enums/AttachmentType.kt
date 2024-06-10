package domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class AttachmentType {
    STORAGE_FILE,
    UPLOAD_FILE,
    EXTERNAL_LINK
}