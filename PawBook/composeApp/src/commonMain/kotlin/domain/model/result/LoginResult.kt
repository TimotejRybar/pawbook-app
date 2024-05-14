package domain.model.result

import kotlinx.serialization.Serializable

@Serializable
data class LoginResult(
    val message: String? = null,
    val user: User? = null,
    val tokens: Tokens? = null
)
