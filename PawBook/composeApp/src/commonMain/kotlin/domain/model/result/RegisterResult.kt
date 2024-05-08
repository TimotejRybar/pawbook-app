package domain.model.result

class RegisterResult(
    error: Boolean,
    message: String,
    val accessToken: String,
    val refreshToken: String
) : BaseResult(error, message)
