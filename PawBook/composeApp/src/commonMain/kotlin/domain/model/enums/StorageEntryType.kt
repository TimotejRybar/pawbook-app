package domain.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class StorageEntryType(val value: String) {
    RETURN("Return"),
    FOLDER("Folder"),
    PDF("PDF"),
    ARCHIVE("Archive"),
    PHOTO("Photo"),
    DOCUMENT("Document"),
    VIDEO("Video"),
    UNKNOWN("Unknown");

    companion object {
        fun fromValue(value: String): StorageEntryType {
            return entries.find { it.value == value } ?: UNKNOWN
        }
    }
}