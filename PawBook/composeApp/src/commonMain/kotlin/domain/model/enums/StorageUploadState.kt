package domain.model.enums

enum class StorageUploadState {
    NO_INTERNET,
    ERROR,
    LOADING,
    UPLOADED_FILE,
    CREATED_FOLDER,
    IDLE,
    INVALID_FILE
}