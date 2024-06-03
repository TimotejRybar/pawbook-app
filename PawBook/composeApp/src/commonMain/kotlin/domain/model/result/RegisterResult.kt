package domain.model.result

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResult(
    val message: String? = null,
    val user: User? = null,
    val accessAndRefreshTokens: Tokens? = null
)

@Serializable
data class User(
    val name: String,
    val email: String,
    val social: String,
    val role: String,
    val isEmailVerified: Boolean,
    val id: String,
    val birthDay: LocalDateTime,
    val city: String
)

@Serializable
data class Tokens(
    val access: TokenInfo,
    val refresh: TokenInfo
)

@Serializable
data class TokenInfo(
    val token: String,
    val expires: LocalDateTime
)
