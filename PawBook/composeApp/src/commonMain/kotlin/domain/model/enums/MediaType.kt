package domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class MediaType(val value: String) {
    Photo("Photo"),
    Video("Video"),
    Unknown("Unknown")
}