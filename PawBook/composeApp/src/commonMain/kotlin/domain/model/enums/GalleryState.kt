package domain.model.enums

enum class GalleryState {
    IDLE,
    LOADING,
    ERROR,
    SUCCESS,
    NO_INTERNET,
    CREATE_ALBUM,
    UPLOAD_PHOTO,
    INVALID_FILE,
    UPLOADED_FILE
}