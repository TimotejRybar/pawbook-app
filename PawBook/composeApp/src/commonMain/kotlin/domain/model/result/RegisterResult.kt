package domain.model.result

data class RegisterResult(
    val error: Boolean,
    val message: String,
    val accessToken: String,
    val refreshToken: String
)
