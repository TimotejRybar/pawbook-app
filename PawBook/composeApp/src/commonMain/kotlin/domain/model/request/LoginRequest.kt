package domain.model.request

@kotlinx.serialization.Serializable
 class LoginRequest(val email: String? = null, val password: String? = null) {
 }