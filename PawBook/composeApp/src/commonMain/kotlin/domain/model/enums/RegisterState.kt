package domain.model.enums

enum class RegisterState {
    IDLE,
    LOADING,
    ERROR,
    SUCCESS,
    NO_INTERNET,
    INVALID_EMAIL
}