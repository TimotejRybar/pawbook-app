package domain.model.enums

enum class PetCreateState {
    INIT,
    VIEW,
    EDIT,
    CREATE,
    DELETE,
    NO_INTERNET,
    LOADING,
    ERROR,
    UPLOADED_PHOTO,
    SAVED
}