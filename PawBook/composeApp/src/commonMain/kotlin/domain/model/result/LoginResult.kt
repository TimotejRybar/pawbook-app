package domain.model.result

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    //val error: Boolean,
    val message: String,
    //val accessToken: String,
    //val refreshToken: String
)
