package domain.model.enums

enum class StorageEntryType(val value: String) {
    RETURN("Return"),
    FOLDER("Folder"),
    PDF("PDF"),
    ARCHIVE("Archive"),
    PHOTO("Photo"),
    DOCUMENT("Document"),
    VIDEO("Video"),
    UNKNOWN("Unknown")
}