package domain.model.result

class LoginResult(
    error: Boolean,
    message: String,
    val accessToken: String,
    val refreshToken: String
) : BaseResult(error, message)
